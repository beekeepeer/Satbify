-- beta version 0.03
-- 2024.08.04
-- by Dmitri Goriuc



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
                reaper.ShowMessageBox("Error: More than one track found for 'main_satbify'", "Error", 1)
                return
            end
            satbify_tracks.main_satbify = i_track
            previous_track_number = reaper.GetMediaTrackInfo_Value(i_track, "IP_TRACKNUMBER")
        elseif track_name:find(".*soprano_satbify.*") then
            if satbify_tracks.soprano_satbify then
                reaper.ShowMessageBox("Error: More than one track found for 'soprano_satbify'", "Error", 1)
                return
            end
            satbify_tracks.soprano_satbify = i_track
        elseif track_name:find(".*alto_satbify.*") then
            if satbify_tracks.alto_satbify then
                reaper.ShowMessageBox("Error: More than one track found for 'alto_satbify'", "Error", 1)
                return
            end
            satbify_tracks.alto_satbify = i_track
        elseif track_name:find(".*tenor_satbify.*") then
            if satbify_tracks.tenor_satbify then
                reaper.ShowMessageBox("Error: More than one track found for 'tenor_satbify'", "Error", 1)
                return
            end
            satbify_tracks.tenor_satbify = i_track
        elseif track_name:find(".*bass_satbify.*") then
            if satbify_tracks.bass_satbify then
                reaper.ShowMessageBox("Error: More than one track found for 'bass_satbify'", "Error", 1)
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

local notes = ""





function process_tracks_in_time_selection(tracks)
    -- Get the time selection start and end times
    local time_sel_start, time_sel_end = reaper.GetSet_LoopTimeRange(false, false, 0, 0, false)

    -- Iterate through each track in the table
    for track_name, track in pairs(tracks) do
        local item_count = reaper.CountTrackMediaItems(track)
        local has_item_in_selection = false

        -- Iterate through each media item on the track
        for i = 0, item_count - 1 do
            local item = reaper.GetTrackMediaItem(track, i)
            local item_start = reaper.GetMediaItemInfo_Value(item, "D_POSITION")
            local item_end = item_start + reaper.GetMediaItemInfo_Value(item, "D_LENGTH")

            -- Case 1: Item starts inside the time selection but ends outside
            if item_start >= time_sel_start and item_start < time_sel_end and item_end > time_sel_end then
                -- Adjust the item's end to match the time selection end
                reaper.SetMediaItemInfo_Value(item, "D_LENGTH", time_sel_end - item_start)
                reaper.UpdateItemInProject(item)
                has_item_in_selection = true

                -- Case 2: Item starts outside the time selection but ends inside
            elseif item_start < time_sel_start and item_end > time_sel_start and item_end <= time_sel_end then
                -- Adjust the item's start offset to maintain MIDI content position
                local take = reaper.GetActiveTake(item)
                local offset = time_sel_start - item_start
                reaper.SetMediaItemTakeInfo_Value(take, "D_STARTOFFS",
                    reaper.GetMediaItemTakeInfo_Value(take, "D_STARTOFFS") + offset)

                -- Set the item's new position and adjust the length
                reaper.SetMediaItemInfo_Value(item, "D_POSITION", time_sel_start)
                reaper.SetMediaItemInfo_Value(item, "D_LENGTH", item_end - time_sel_start)
                reaper.UpdateItemInProject(item)
                has_item_in_selection = true

                -- Case 3: Item already covers the entire time selection
            elseif item_start <= time_sel_start and item_end >= time_sel_end then
                has_item_in_selection = true
            end
        end

        -- Case 4: No item exists in the time selection, create a new MIDI item
        if not has_item_in_selection then
            local new_item = reaper.CreateNewMIDIItemInProj(track, time_sel_start, time_sel_end, false)
            reaper.UpdateItemInProject(new_item)
        end
    end
end
process_tracks_in_time_selection(find_tracks())


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
