-- beta version 0.03
-- 2024.08.04
-- by Dmitri Goriuc


function print(str)
    reaper.ShowConsoleMsg(str .. "\n")
end



local proj = reaper.EnumProjects(-1, "") -- -1 means the currently active project
local previous_track_number = 0

-- Function to organize tracks for satbify
function find_tracks()
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
                reaper.ShowMessageBox("Error: More than one track found for 'main_satbify'", "Error", 0)
                return
            end
            satbify_tracks.main_satbify = i_track
            previous_track_number = reaper.GetMediaTrackInfo_Value(i_track, "IP_TRACKNUMBER")
        elseif track_name:find(".*soprano_satbify.*") then
            if satbify_tracks.soprano_satbify then
                reaper.ShowMessageBox("Error: More than one track found for 'soprano_satbify'", "Error", 0)
                return
            end
            satbify_tracks.soprano_satbify = i_track
        elseif track_name:find(".*alto_satbify.*") then
            if satbify_tracks.alto_satbify then
                reaper.ShowMessageBox("Error: More than one track found for 'alto_satbify'", "Error", 0)
                return
            end
            satbify_tracks.alto_satbify = i_track
        elseif track_name:find(".*tenor_satbify.*") then
            if satbify_tracks.tenor_satbify then
                reaper.ShowMessageBox("Error: More than one track found for 'tenor_satbify'", "Error", 0)
                return
            end
            satbify_tracks.tenor_satbify = i_track
        elseif track_name:find(".*bass_satbify.*") then
            if satbify_tracks.bass_satbify then
                reaper.ShowMessageBox("Error: More than one track found for 'bass_satbify'", "Error", 0)
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
        end
        previous_track_number = reaper.GetMediaTrackInfo_Value(track, "IP_TRACKNUMBER")
    end
    return satbify_tracks
end







function read_notes(tracks)
    local notes = ""
    local satbify_takes = {} -- return this array
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
        -- Iterate through each track in the table
        for track_name, track in pairs(tracks) do
            local track_nubmer = reaper.GetMediaTrackInfo_Value(track, "IP_TRACKNUMBER")
            local item_count = reaper.CountTrackMediaItems(track)
            local has_item_in_selection = false
            -- Iterate through each media item on the track
            for i = 0, item_count - 1 do
                local item = reaper.GetTrackMediaItem(track, i)
                local item_start = reaper.GetMediaItemInfo_Value(item, "D_POSITION")
                local item_end = item_start + reaper.GetMediaItemInfo_Value(item, "D_LENGTH")
                -- if Item starts inside the time selection but ends outside
                if item_start > time_sel_start and item_start < time_sel_end and item_end > time_sel_end  then
                    reaper.SetMediaItemSelected(item, true)
                    reaper.ApplyNudge(0, 0, 1, 1, (item_start - time_sel_start), true, 0)
                    reaper.SetMediaItemSelected(item, false)
                    has_item_in_selection = true
                    reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    table.insert(satbify_takes, reaper.GetActiveTake(item))
                    -- if Item starts outside the time selection but ends inside
                elseif item_start < time_sel_start and item_end > time_sel_start and item_end < time_sel_end then
                    reaper.SetMediaItemSelected(item, true)
                    reaper.ApplyNudge(0, 0, 3, 1, (item_end - time_sel_end), true, 0)
                    reaper.SetMediaItemSelected(item, false)
                    has_item_in_selection = true
                    reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    table.insert(satbify_takes, reaper.GetActiveTake(item))
                    -- if Item starts and ends in time selection
                elseif item_start > time_sel_start and item_end < time_sel_end then
                    reaper.SetMediaItemSelected(item, true)
                    reaper.ApplyNudge(0, 0, 1, 1, (item_start - time_sel_start), true, 0)
                    reaper.ApplyNudge(0, 0, 3, 1, (item_end - time_sel_end), true, 0)
                    reaper.SetMediaItemSelected(item, false)
                    has_item_in_selection = true
                    reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    table.insert(satbify_takes, reaper.GetActiveTake(item))
                    -- if Item already covers the entire time selection
                elseif item_start <= time_sel_start and item_end >= time_sel_end then
                    has_item_in_selection = true
                    reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    table.insert(satbify_takes, reaper.GetActiveTake(item))
                end
            end
            -- if No item exists in the time selection, create a new MIDI item
            if not has_item_in_selection then
                local new_item = reaper.CreateNewMIDIItemInProj(track, time_sel_start, time_sel_end, false)
                reaper.UpdateItemInProject(new_item)
                reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(new_item), "P_NAME", track_name, true)
                table.insert(satbify_takes, reaper.GetActiveTake(new_item))
            end
        end
    else
        reaper.ShowMessageBox("Satbify: Please, Set Time Selection", "Error", 0)
    end
    -- restore item selection
    if sel_item_before then
        reaper.SetMediaItemSelected(sel_item_before, true)
    end
    local time_sel_start, time_sel_end = reaper.GetSet_LoopTimeRange(false, false, 0, 0, false)

    -- Iterate through each take in the satbify_takes array
    for _, take in ipairs(satbify_takes) do
        -- Get the track of the take
        local track = reaper.GetMediaItemTake_Track(take)
        -- Get the track number todo: make 1-based on backend
        local track_num = reaper.GetMediaTrackInfo_Value(track, "IP_TRACKNUMBER") -1

        -- Get the number of MIDI notes in the take
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
    return notes
end

local tracks = find_tracks()
local notes_from_reaper = read_notes(tracks)



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
local response_body, response_code = send_http_request(url, notes_from_reaper)
-- reaper.ShowConsoleMsg(response_body)

goto exit;
-- todo make a option to ignore notes on voices tracks

function deleteNotesFromFirstItemOnTracks(tracks)
    -- Loop through the specified tracks
    for _, trackNumber in ipairs(tracks) do
        -- Get the track by its number (1-based index)
        local track = reaper.GetTrack(0, trackNumber - 1)

        if track then
            -- Get the number of items on the track
            local numItems = reaper.CountTrackMediaItems(track)

            if numItems > 0 then
                -- Get the first item on the track
                local item = reaper.GetTrackMediaItem(track, 0)

                if item then
                    -- Get the take from the first item (assuming the item has only one take)
                    local take = reaper.GetActiveTake(item)

                    if take and reaper.TakeIsMIDI(take) then
                        -- Delete all notes in the take
                        local noteIndex = reaper.MIDI_CountEvts(take)
                        while noteIndex > 0 do
                            reaper.MIDI_DeleteNote(take, noteIndex - 1)
                            noteIndex = noteIndex - 1
                        end

                        -- Optional: Clean up the MIDI take (remove empty events)
                        reaper.MIDI_Sort(take)
                    end
                end
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

-- Function to insert a note into Reaper
function insert_note(track_num, note_num, ppq_start, ppq_end)
    local track = reaper.GetTrack(0, track_num)
    if not track then
        reaper.ShowConsoleMsg("Track " .. track_num .. " not found.\n")
        return
    end

    local midi_item = reaper.GetTrackMediaItem(track, 0)
    if not midi_item then
        reaper.ShowConsoleMsg("No MIDI item found on track " .. track_num .. ".\n")
        return
    end

    local take = reaper.GetTake(midi_item, 0)
    if not take or not reaper.TakeIsMIDI(take) then
        reaper.ShowConsoleMsg("Failed to get a valid MIDI take from the MIDI item.\n")
        return
    end
    reaper.MIDI_InsertNote(take, false, false, ppq_start, ppq_end, 0, note_num, 100, false)
    reaper.MIDI_Sort(take)
    reaper.UpdateArrange()
end

-- Function to parse the HTTP response and insert notes accordingly
function parse_and_insert_notes(response_body)
    if isValidFormat(response_body) then
        deleteNotesFromFirstItemOnTracks({ 2, 3, 4, 5 })
    end
    for line in response_body:gmatch("[^\r\n]+") do
        local track_num, note_num, ppq_start, ppq_end = line:match("(%d+), (%d+), (%d+), (%d+)")
        if track_num and note_num and ppq_start and ppq_end then
            track_num = tonumber(track_num)
            note_num = tonumber(note_num)
            ppq_start = tonumber(ppq_start)
            ppq_end = tonumber(ppq_end)
            insert_note(track_num, note_num, ppq_start, ppq_end)
        else
            reaper.ShowConsoleMsg("I can't tackle this just yet... " .. "\n")
        end
    end
end

-- Display the response in the Reaper console and insert notes
-- if response_body and response_code == 200 then
parse_and_insert_notes(response_body)
-- else
-- reaper.ShowConsoleMsg("Failed to execute HTTP request or no valid response received.\n")
-- end

reaper.Undo_EndBlock("Insert MIDI notes from HTTP response", -1)

-- label at last line
::exit::
