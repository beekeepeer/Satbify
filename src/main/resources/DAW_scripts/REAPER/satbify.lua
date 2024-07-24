-- Function to read MIDI notes from Reaper
function read_notes()
    local notes = ""

    for track_num = 0, 4 do  -- Loop through the first five tracks (0-based index)
        local track = reaper.GetTrack(0, track_num)
        if not track then
            reaper.ShowConsoleMsg("Track " .. (track_num + 1) .. " not found.\n")
            return notes
        end

        -- Get the first MIDI item on the track
        local midi_item = reaper.GetTrackMediaItem(track, 0)
        if not midi_item then
            reaper.ShowConsoleMsg("No MIDI item found on track " .. (track_num + 1) .. ".\n")
            return notes
        end

        -- Get the take from the MIDI item
        local take = reaper.GetTake(midi_item, 0)
        if not take or not reaper.TakeIsMIDI(take) then
            reaper.ShowConsoleMsg("Failed to get a valid MIDI take from the MIDI item on track " .. (track_num + 1) .. ".\n")
            return notes
        end

        -- Loop through all notes in the take
        local retval, note_count = reaper.MIDI_CountEvts(take)
        for i = 0, note_count - 1 do
            local retval, selected, muted, startppqpos, endppqpos, chan, pitch, vel = reaper.MIDI_GetNote(take, i)
            notes = notes .. string.format("%d, %d, %d, %d\n", track_num, pitch, startppqpos, endppqpos)
        end
    end

    return notes
end

local notes_from_reaper = read_notes()


-- send HTTP POST request using curl
function send_http_request(url, body)
    -- Write the body to a temporary file
    local temp_body_file = os.tmpname()
    local file = io.open(temp_body_file, "w")
    file:write(body)
    file:close()

    -- Use curl to send the HTTP request and capture the response
    local temp_response_file = os.tmpname()
    local command = string.format('curl -s -o %s -w "%%{http_code}" -X POST -H "Content-Type: application/json" --data-binary @"%s" "%s"', temp_response_file, temp_body_file, url)
    local response_code = os.execute(command)

    -- Read the response from the temporary file
    file = io.open(temp_response_file, "r")
    local response_body = file:read("*all")
    file:close()

    -- Clean up temporary files
    os.remove(temp_body_file)
    os.remove(temp_response_file)

    return response_body, response_code
end

local url = "http://localhost:8080/api"
--local url = "https://satbify.up.railway.app/api"
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
        deleteNotesFromFirstItemOnTracks({2, 3, 4, 5})
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
                reaper.ShowConsoleMsg("Apologies, I can't tackle this just yet... but I'm hitting the books and sharpening my skills. Stay tuned for my upgraded brilliance!" .. "\n")
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
