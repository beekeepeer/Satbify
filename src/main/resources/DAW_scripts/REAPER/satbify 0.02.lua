-- beta version 0.03
-- 2024.08.04
-- by Dmitri Goriuc


function print(str)
    reaper.ShowConsoleMsg(str .. "\n")
end



local proj = reaper.EnumProjects(-1, "") -- -1 means the currently active project
local flagExit = false

-- Function to organize tracks for satbify
function find_tracks()
    local previous_track_number = 0

    local satbify_tracks = {
        main_satbify = nil,
        soprano_satbify = nil,
        alto_satbify = nil,
        tenor_satbify = nil,
        bass_satbify = nil
    }
    local track_names = { { 0, 'main_satbify' }, { 1, 'soprano_satbify' }, { 2, 'alto_satbify' }, { 3, 'tenor_satbify' }, { 4, 'bass_satbify' } }
    -- Loop through all tracks to find the existing ones
    for i = 0, reaper.CountTracks(0) - 1 do
        local i_track = reaper.GetTrack(0, i)
        local _, track_name = reaper.GetTrackName(i_track)
        track_name = track_name:lower()
        if track_name:find(".*main_satbify.*") then
            if satbify_tracks.main_satbify then
                reaper.ShowMessageBox("More than one track found for 'main_satbify'", "Satbify", 0)
                flagExit = true
                return
            end
            satbify_tracks.main_satbify = i_track
            previous_track_number = reaper.GetMediaTrackInfo_Value(i_track, "IP_TRACKNUMBER")
        elseif track_name:find(".*soprano_satbify.*") then
            if satbify_tracks.soprano_satbify then
                reaper.ShowMessageBox("More than one track found for 'soprano_satbify'", "Satbify", 0)
                flagExit = true
                return
            end
            satbify_tracks.soprano_satbify = i_track
        elseif track_name:find(".*alto_satbify.*") then
            if satbify_tracks.alto_satbify then
                reaper.ShowMessageBox("More than one track found for 'alto_satbify'", "Satbify", 0)
                flagExit = true
                return
            end
            satbify_tracks.alto_satbify = i_track
        elseif track_name:find(".*tenor_satbify.*") then
            if satbify_tracks.tenor_satbify then
                reaper.ShowMessageBox("More than one track found for 'tenor_satbify'", "Satbify", 0)
                flagExit = true
                return
            end
            satbify_tracks.tenor_satbify = i_track
        elseif track_name:find(".*bass_satbify.*") then
            if satbify_tracks.bass_satbify then
                reaper.ShowMessageBox("More than one track found for 'bass_satbify'", "Satbify", 0)
                flagExit = true
                return
            end
            satbify_tracks.bass_satbify = i_track
        end
    end
    -- add absent tracks
    for i = 1, #track_names do
        local name = track_names[i][2]
        local track = satbify_tracks[name]
        if not track then
            reaper.InsertTrackInProject(proj, previous_track_number, 0)
            track = reaper.GetTrack(proj, previous_track_number)
            reaper.GetSetMediaTrackInfo_String(track, "P_NAME", name, true)
            satbify_tracks[name] = track
            if name:find(".*main_satbify.*") then
                flagExit = true
            end
        end
        previous_track_number = reaper.GetMediaTrackInfo_Value(track, "IP_TRACKNUMBER")
    end
    return satbify_tracks
end







function read_notes(tracks)
    local notes = ""
    local satbify_takes = {} -- return this array
    local mainTimeOffset
    local track_index = 0
    local sel_item_before = reaper.GetSelectedMediaItem(0, 0)
    -- unselect all items:
    local num_items = reaper.CountMediaItems(0) -- Get the number of items in the current project
    for i = 0, num_items - 1 do
        local current_item = reaper.GetMediaItem(0, i)
        reaper.SetMediaItemSelected(current_item, false) -- Unselect each item
    end
    -- Get the time selection start and end times
    local time_sel_start, time_sel_end = reaper.GetSet_LoopTimeRange(false, false, 0, 0, false)
    if (time_sel_end - time_sel_start) > 0 then
        local isMain = false
        -- Iterate through each track in the table
        for track_name, track in pairs(tracks) do
            if track_name == "main_satbify" then
                track_index = 0
                isMain = true
            elseif track_name == "soprano_satbify" then
                track_index = 1
            elseif track_name == "alto_satbify" then
                track_index = 2
            elseif track_name == "tenor_satbify" then
                track_index = 3
            elseif track_name == "bass_satbify" then
                track_index = 4
            end
            local track_nubmer = reaper.GetMediaTrackInfo_Value(track, "IP_TRACKNUMBER")
            local item_count = reaper.CountTrackMediaItems(track)
            local no_items_chosen = true
            -- Iterate through each media item on the track
            for i = 0, item_count - 1 do
                local item = reaper.GetTrackMediaItem(track, i)
                local item_start = reaper.GetMediaItemInfo_Value(item, "D_POSITION")
                local item_end = item_start + reaper.GetMediaItemInfo_Value(item, "D_LENGTH")
                -- if Item starts inside the time selection but ends outside
                if item_start > time_sel_start and item_start < time_sel_end and item_end > time_sel_end and no_items_chosen then
                    if not isMain then
                        reaper.SetMediaItemSelected(item, true)
                        reaper.ApplyNudge(0, 0, 1, 1, (item_start - time_sel_start), true, 0)
                        reaper.SetMediaItemSelected(item, false)
                        reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    end
                    no_items_chosen = false
                    table.insert(satbify_takes, {reaper.GetActiveTake(item), track_index})
                    -- if Item starts outside the time selection but ends inside
                elseif item_start <= time_sel_start and item_end > time_sel_start and item_end < time_sel_end and no_items_chosen then
                    if not isMain then
                        reaper.SetMediaItemSelected(item, true)
                        reaper.ApplyNudge(0, 0, 3, 1, (item_end - time_sel_end), true, 0)
                        reaper.SetMediaItemSelected(item, false)
                        reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    end
                    no_items_chosen = false
                    table.insert(satbify_takes, {reaper.GetActiveTake(item), track_index})
                    -- if Item starts and ends in time selection
                elseif item_start > time_sel_start and item_end < time_sel_end and no_items_chosen then
                    if not isMain then
                        reaper.SetMediaItemSelected(item, true)
                        reaper.ApplyNudge(0, 0, 1, 1, (item_start - time_sel_start), true, 0)
                        reaper.ApplyNudge(0, 0, 3, 1, (item_end - time_sel_end), true, 0)
                        reaper.SetMediaItemSelected(item, false)
                        reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    end
                    no_items_chosen = false
                    table.insert(satbify_takes, {reaper.GetActiveTake(item), track_index})
                    -- if Item already covers the entire time selection
                elseif item_start <= time_sel_start and item_end >= time_sel_end and no_items_chosen then
                    no_items_chosen = false
                    reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    table.insert(satbify_takes, {reaper.GetActiveTake(item), track_index})
                end
            end
            -- if No item exists in the time selection, create a new MIDI item
            if no_items_chosen then
                local new_item = reaper.CreateNewMIDIItemInProj(track, time_sel_start, time_sel_end, false)
                reaper.UpdateItemInProject(new_item)
                reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(new_item), "P_NAME", track_name, true)
                table.insert(satbify_takes, {reaper.GetActiveTake(new_item), track_index})
                if track_name == "main_satbify" then
                    flagExit = true
                end
            end
        end
    else
        reaper.ShowMessageBox("Please, Set Time Selection", "Satbify", 0)
        flagExit = true
    end
    -- restore item selection
    if sel_item_before then
        reaper.SetMediaItemSelected(sel_item_before, true)
    end
    local time_sel_start, time_sel_end = reaper.GetSet_LoopTimeRange(false, false, 0, 0, false)

    -- Iterate through each take in the satbify_takes array
    for i, take_info in ipairs(satbify_takes) do
            local take = take_info[1] -- The take object
            local track_num = take_info[2] -- The track index
        local track = reaper.GetMediaItemTake_Track(take)
        local _, note_count = reaper.MIDI_CountEvts(take)
        -- Iterate through each MIDI note in the take
        for i = 0, note_count - 1 do
            local retval, selected, muted, startppqpos, endppqpos, chan, pitch, vel = reaper.MIDI_GetNote(take, i)
            -- Convert MIDI start position (PPQ) to project time
            local note_start_time = reaper.MIDI_GetProjTimeFromPPQPos(take, startppqpos)
            local note_end_time = reaper.MIDI_GetProjTimeFromPPQPos(take, endppqpos)

            -- Only process the note if it's not muted and within the time selection
            if not muted and note_start_time >= time_sel_start and note_start_time <= time_sel_end then
                -- Add note information to the string 'notes' in the required format
                notes = notes .. string.format("%d,%d,%d,%d-", track_num, pitch, startppqpos, endppqpos)
            end
        end
    end
    --print(notes)
    return notes, satbify_takes
end

local tracks = find_tracks()
local notes_from_reaper, satbify_takes, timeOffset = read_notes(tracks)



-- if you need to get the track of a take: MediaTrack reaper.GetMediaItemTake_Track(MediaItem_Take take)
-- if you need to get the name of a take: string reaper.GetTakeName(MediaItem_Take take)

-- send HTTP POST request using curl
function send_http_request(url, body)
    -- Escape special characters in the body to ensure the curl command works correctly
    local escaped_body = string.gsub(body, '"', '\\"')
    -- Use curl to send the HTTP request and capture the response directly
    local command = string.format('curl -s -w "%%{http_code}" -X POST -H "Content-Type: application/json" -d "%s" "%s"',
        escaped_body, url)
    --windows are not familiar with used new SSL/TLS certificate verification:
    local handle = io.popen(command .. " --insecure")
    --reaper.ShowConsoleMsg(command)
    local result = handle:read("*all")
    handle:close()
    -- Extract the response body and the response code
    local response_code = string.sub(result, -3)
    local response_body = string.sub(result, 1, -4)
    return response_body, response_code
end

--local url = "http://localhost:8080/api" -- testing locally
local url = "https://satbify.up.railway.app/api"
local response_body
local  response_code
if not flagExit then
    response_body, response_code = send_http_request(url, notes_from_reaper)
end
-- reaper.ShowConsoleMsg(response_body)


--
-- todo make a option to ignore notes on voices tracks
-- todo: return from backend to the same takes using index from satbify_takes table

function deleteNotesInTimeSelection(table_takes)
    -- Get the current project time selection start and end
    local timeSelStart, timeSelEnd = reaper.GetSet_LoopTimeRange(false, false, 0, 0, false)
    -- Loop through each take provided in the table
    for i, take_info in ipairs(table_takes) do
        local take = take_info[1] -- The take object
        local track_num = take_info[2] -- The track index
        -- Get PPQ positions corresponding to the project's time selection for this take
        local start_ppq = reaper.MIDI_GetPPQPosFromProjTime(take, timeSelStart)
        local end_ppq = reaper.MIDI_GetPPQPosFromProjTime(take, timeSelEnd)
        -- Count the number of notes in the take
        local retval, notesCount, _, _ = reaper.MIDI_CountEvts(take)
        -- Loop through the notes in reverse order to avoid messing up the note indices when deleting
        for noteIndex = notesCount - 1, 0, -1 do
            local retval, selected, muted, startppqpos, endppqpos, chan, pitch, vel = reaper.MIDI_GetNote(take, noteIndex)
            -- Check if the note start is within the time selection
            if startppqpos >= start_ppq and startppqpos < end_ppq and track_num > 0 then
                reaper.MIDI_DeleteNote(take, noteIndex)
            end
        end
    end
end

function isValidFormat(str)
    -- Pattern to match the specific format of each line
    local pattern = "^%d+, %d+, %d+, %d+$"
    -- Loop through each line in the string
    for line in str:gmatch("[^\r\n]+") do
        -- Check if the line matches the pattern
        if not line:match(pattern) then
            return false
        end
    end
    return true
end

-- Function to parse the HTTP response and insert notes accordingly
function parse_and_insert_notes(response, takes)
    local mainTake
    for i = 1, #takes do
        -- Accessing the second element of the inner table (track_index)
        if takes[i][2] == 0 then
            mainTake = takes[i][1] -- Return the whole element {take, track_index}
            goto mainFound
        end
    end
    ::mainFound::

    -- Check if the response format is valid and delete existing notes within the time selection
    if isValidFormat(response) then
        deleteNotesInTimeSelection(takes)
    end

    -- Loop through each line of the response body
    for line in response:gmatch("[^\r\n]+") do
        -- Parse the note data from the line
        local track_num, note_num, ppq_start, ppq_end = line:match("(%d+), (%d+), (%d+), (%d+)")

        -- Check if all required data was successfully parsed
        if track_num and note_num and ppq_start and ppq_end then
            -- Convert parsed data from string to numbers
            track_num = tonumber(track_num)
            note_num = tonumber(note_num)
            ppq_start = tonumber(ppq_start)
            ppq_end = tonumber(ppq_end)

            -- Loop through the provided takes to find the correct track to insert the note
            for i, take_info in ipairs(takes) do
                local take = take_info[1] -- The take object
                local take_track_index = take_info[2] -- The track index
                local startTimeFromMain = reaper.MIDI_GetProjTimeFromPPQPos(mainTake, ppq_start)
                local endTimeFromMain = reaper.MIDI_GetProjTimeFromPPQPos(mainTake, ppq_end)
                local note_ppq_start = reaper.MIDI_GetPPQPosFromProjTime(take, startTimeFromMain)
                local note_ppq_end = reaper.MIDI_GetPPQPosFromProjTime(take, endTimeFromMain)

                -- If the take's track number matches the parsed track number, insert the note
                if take_track_index == track_num then
                    -- Adjust PPQ positions by adding the timeOffset
                    --local note_ppq_start = reaper.MIDI_GetPPQPosFromProjTime(take, ppq_start)
                    --local note_ppq_end = reaper.MIDI_GetPPQPosFromProjTime(take, ppq_end)

                    -- Insert the note into the MIDI take
                    reaper.MIDI_InsertNote(
                            take,
                            false, -- selected
                            false, -- muted
                            note_ppq_start, -- startppqpos
                            note_ppq_end,   -- endppqpos
                            0, -- channel (0-15, we'll assume 0)
                            note_num, -- pitch
                            100, -- velocity (0-127, we'll assume 100)
                            false) -- noSort
                end
            end
        elseif flagExit then -- Do not print anything if main track is created only now
        else
            -- Log an error message if parsing the line failed
            reaper.ShowConsoleMsg("I can't tackle this just yet... " .. "\n")
        end
    end

    -- Sort MIDI data after inserting notes
    for _, take_info in ipairs(takes) do
        local take = take_info[1]
        reaper.MIDI_Sort(take)
    end
end


-- Display the response in the Reaper console and insert notes
-- if response_body and response_code == 200 then
if not flagExit then
    parse_and_insert_notes(response_body, satbify_takes)
end

-- else
-- reaper.ShowConsoleMsg("Failed to execute HTTP request or no valid response received.\n")
-- end

reaper.Undo_EndBlock("Insert MIDI notes from HTTP response", -1)

-- label at last line
::exit::
