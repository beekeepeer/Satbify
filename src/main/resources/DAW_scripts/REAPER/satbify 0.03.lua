-- beta version 0.03
-- 2024.09.09
-- by Dmitri Goriuc

--[[
       What is new
Important:
- notes are inserted in global time regardless item position
- Satbify now works only within time selection: note start should be within time selection
- muted notes are ignored.

Less important:
- Satbify tracks can now be at any position in the project (no need for making them first 5 tracks anymore).
- required tracks are now created and named automatically if absent.
- Satbify tracks are required to contain one of the following in the track names: 'main_satbify', 'soprano_satbify', 'alto_satbify', 'tenor_satbify', 'bass_satbify
- If there is no track(s) which name contains one of required names, a new track(s) will be added and named.
- if absent, new items will be created, or existing will bee resize to cover time selection - automatically
- more than one items on a main_satbify track work now within time selection. (on other tracks items are still limited to one)
- Use keyswitch C1 if you wnt to harmonize given notes on the voice. Otherwise all the notes will be deleted.
--]]

-- package.cpath = package.cpath .. ";" .. reaper.GetResourcePath() ..'/Scripts/Classes Mobdebug/socket module/?.dll'    -- WINDOWS ONLY: Add socket module path for .dll files
-- package.path = package.path .. ";" .. reaper.GetResourcePath()   ..'/Scripts/Classes Mobdebug/socket module/?.lua'      -- Add all lua socket modules to the path  

-- require("mobdebug").start() -- Start mobdebug module


function print(str)
    if str == nil then
        str = "there is nothing to print"
    end
    reaper.ShowConsoleMsg(str .. "\n")
end

local url = "http://localhost:8080/api" -- testing locally
--local url = "https://satbify.up.railway.app/api"
--local url = "https://satbify-debug.up.railway.app/api"
local customerId = "12345"
local token = "a7f5c3b7d98ef43e6a5a7c8b9d3e1f6d"
local proj = reaper.EnumProjects(-1, "") -- -1 means the currently active project
local flagExit = false
local sel_item_before, tracks, notes_from_reaper, satbify_takes, timeSelStart, timeSelEnd, hs, ha, ht, hb, hkswOffS, hkswOffA, hkswOffT, hkswOffB
local hksw = 24

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
    local notes = string.format("{\"customerId\":\"%s\",\"token\":\"%s\",\"notes\":[", customerId, token)
    local satbify_takes = {} -- return this array
    local additional_main_takes = {}
    local track_index = 0
    local main_take = 0
    sel_item_before = reaper.GetSelectedMediaItem(0, 0)
    -- unselect all items:
    local num_items = reaper.CountMediaItems(0) -- Get the number of items in the current project
    for i = 0, num_items - 1 do
        local current_item = reaper.GetMediaItem(0, i)
        reaper.SetMediaItemSelected(current_item, false) -- Unselect each item
    end
    -- Get the time selection start and end times
    timeSelStart, timeSelEnd = reaper.GetSet_LoopTimeRange(false, false, 0, 0, false)
    if (timeSelEnd - timeSelStart) > 0 then
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
            local item_count = reaper.CountTrackMediaItems(track)
            local no_items_chosen = true
            -- Iterate through each media item on the track
            for i = 0, item_count - 1 do
                local item = reaper.GetTrackMediaItem(track, i)
                local item_start = reaper.GetMediaItemInfo_Value(item, "D_POSITION")
                local item_end = item_start + reaper.GetMediaItemInfo_Value(item, "D_LENGTH")
                -- if Item starts inside the time selection but ends outside
                if isMain and not no_items_chosen then
                    table.insert(additional_main_takes, reaper.GetActiveTake(item))
                end
                    -- if item starts in time selection but ends outside
                if item_start >= timeSelStart and item_start <= timeSelEnd and item_end >= timeSelEnd and no_items_chosen then
                    if not isMain then
                        reaper.SetMediaItemSelected(item, true)
                        reaper.ApplyNudge(0, 0, 1, 1, (item_start - timeSelStart), true, 0)
                        reaper.SetMediaItemSelected(item, false)
                        reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    end
                    no_items_chosen = false
                    table.insert(satbify_takes, {reaper.GetActiveTake(item), track_index})
                    -- if Item starts outside the time selection but ends inside
                elseif item_start <= timeSelStart and item_end >= timeSelStart and item_end <= timeSelEnd and no_items_chosen then
                    if not isMain then
                        reaper.SetMediaItemSelected(item, true)
                        reaper.ApplyNudge(0, 0, 3, 1, (item_end - timeSelEnd), true, 0)
                        reaper.SetMediaItemSelected(item, false)
                        reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    end
                    no_items_chosen = false
                    table.insert(satbify_takes, {reaper.GetActiveTake(item), track_index})
                    -- if Item starts and ends in time selection
                elseif item_start >= timeSelStart and item_end <= timeSelEnd and no_items_chosen then
                    if not isMain then
                        reaper.SetMediaItemSelected(item, true)
                        reaper.ApplyNudge(0, 0, 1, 1, (item_start - timeSelStart), true, 0)
                        reaper.ApplyNudge(0, 0, 3, 1, (item_end - timeSelEnd), true, 0)
                        reaper.SetMediaItemSelected(item, false)
                        reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    end
                    no_items_chosen = false
                    table.insert(satbify_takes, {reaper.GetActiveTake(item), track_index})
                    -- if Item already covers the entire time selection
                elseif item_start <= timeSelStart and item_end >= timeSelEnd and no_items_chosen then
                    no_items_chosen = false
                    reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(item), "P_NAME", track_name, true)
                    table.insert(satbify_takes, {reaper.GetActiveTake(item), track_index})
                end
            end
            -- if No item exists in the time selection, create a new MIDI item
            if no_items_chosen then
                local new_item = reaper.CreateNewMIDIItemInProj(track, timeSelStart, timeSelEnd, false)
                reaper.UpdateItemInProject(new_item)
                reaper.GetSetMediaItemTakeInfo_String(reaper.GetActiveTake(new_item), "P_NAME", track_name, true)
                table.insert(satbify_takes, {reaper.GetActiveTake(new_item), track_index})
                if track_name == "main_satbify" then
                    flagExit = true
                end
            end
            isMain = false
        end
    else
        reaper.ShowMessageBox("Please, Set Time Selection", "Satbify", 0)
        flagExit = true
    end

    -- Iterate through each take in the satbify_takes array
    for i, take_info in ipairs(satbify_takes) do
            local take = take_info[1] -- The take object
            local track_num = take_info[2] -- The track index
        local _, note_count = reaper.MIDI_CountEvts(take)
        -- Iterate through each MIDI note in the take
        for i = 0, note_count - 1 do
            local retval, selected, muted, startppqpos, endppqpos, chan, pitch, vel = reaper.MIDI_GetNote(take, i)
            -- Convert MIDI start position (PPQ) to project time
            local note_start_time = reaper.MIDI_GetProjTimeFromPPQPos(take, startppqpos)
            local note_end_time = reaper.MIDI_GetProjTimeFromPPQPos(take, endppqpos)

            local start_retval, start_measures, start_cml, start_fullbeats, start_cdenom = reaper.TimeMap2_timeToBeats(0, note_start_time)
            local end_retval, end_measures, end_cml, end_fullbeats, end_cdenom = reaper.TimeMap2_timeToBeats(0, note_end_time)
            -- start_bar = start_bar + 1  -- Convert 0-based to 1-based index
            -- end_bar = end_bar + 1      -- Convert 0-based to 1-based index

            if track_num == 1 and pitch == hksw then
                hs = true
                hkswOffS = note_end_time
            elseif track_num == 2 and pitch == hksw then
                ha = true
                hkswOffA = note_end_time
            elseif track_num == 3 and pitch == hksw then
                ht = true
                hkswOffT = note_end_time
            elseif track_num == 4 and pitch == hksw then
                hb = true
                hkswOffB = note_end_time
            end

            -- Only process the note if it's not muted, within the time selection or should be harmonised
            if not muted and note_start_time >= timeSelStart - 0.01 and note_start_time <= timeSelEnd  - 0.01
                    and (track_num == 0
                    or (track_num == 1 and hs and hkswOffS > note_start_time)
                    or (track_num == 2 and ha and hkswOffA > note_start_time)
                    or (track_num == 3 and ht and hkswOffT > note_start_time)
                    or (track_num == 4 and hb and hkswOffB > note_start_time))
                    and pitch ~= hksw
            then
                -- Add note information to the string 'notes' in the required format
                notes = notes ..  string.format(
                    '{"track":%d,"note":%d,"velocity":%d,"start":%.4f,"end":%.4f,"startBar":%d,"endBar":%d,"startBeat":%.3f,"endBeat":%.3f},',
                    track_num, pitch, vel, note_start_time, note_end_time, start_measures, end_measures, start_fullbeats, end_fullbeats
                )
            end
        end
        if track_num == 0 then
            main_take = take
        end
    end

    -- Iterate through each take in the additional_main_takes array
    for i, take in ipairs(additional_main_takes) do
        local _, note_count = reaper.MIDI_CountEvts(take)
        -- Iterate through each MIDI note in the take
        for i = 0, note_count - 1 do
            local retval, selected, muted, startppqpos, endppqpos, chan, pitch, vel = reaper.MIDI_GetNote(take, i)
            -- Convert MIDI start position (PPQ) to project time
            local note_start_time = reaper.MIDI_GetProjTimeFromPPQPos(take, startppqpos)
            local note_end_time =   reaper.MIDI_GetProjTimeFromPPQPos(take, endppqpos)
            local start_retval, start_measures, start_cml, start_fullbeats, start_cdenom = reaper.TimeMap2_timeToBeats(0, note_start_time)
            local end_retval, end_measures, end_cml, end_fullbeats, end_cdenom = reaper.TimeMap2_timeToBeats(0, note_end_time)
            start_bar = start_bar + 1  -- Convert 0-based to 1-based index
            end_bar = end_bar + 1      -- Convert 0-based to 1-based index
 -- Only process the note if it's not muted and within the time selection
            if not muted and note_start_time >= timeSelStart - 0.01 and note_start_time <= timeSelEnd  - 0.01 then
                -- Add note information to the string 'notes' in the required format
                notes = notes ..  string.format(
                    '{"track":%d,"note":%d,"velocity":%d,"start":%.4f,"end":%.4f,"startBar":%d,"endBar":%d,"startBeat":%.3f,"endBeat":%.3f},',
                    track_num, pitch, vel, note_start_time, note_end_time, start_measures, end_measures, start_fullbeats, end_fullbeats
                )
            end
        end
    end
    notes = string.sub(notes, 1, -2) .. "]}"
    --print(notes)
    return notes, satbify_takes
end

if not flagExit then
    tracks = find_tracks()
end
if not flagExit then
    notes_from_reaper, satbify_takes = read_notes(tracks)
end

-- send HTTP POST request using curl
function send_http_request(url, body)
    -- Escape special characters in the body to ensure the curl command works correctly
    local escaped_body = string.gsub(body, '"', '\\"')
    -- print(escaped_body)
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


local response_body
local  response_code
if not flagExit then
    response_body, response_code = send_http_request(url, notes_from_reaper)
end
-- print(response_body)

function deleteNotesInTimeSelection(table_takes)
    --print("delete method is called, time selection:" .. timeSelStart)
    for i, take_info in ipairs(table_takes) do
        local take = take_info[1] -- The take object
        local track_num = take_info[2] -- The track index
        local start_ppq = reaper.MIDI_GetPPQPosFromProjTime(take, timeSelStart)
        local end_ppq = reaper.MIDI_GetPPQPosFromProjTime(take, timeSelEnd)
        local retval, notesCount, _, _ = reaper.MIDI_CountEvts(take)
        -- Loop through the notes in reverse order to avoid messing up the note indices when deleting
        for noteIndex = notesCount - 1, 0, -1 do
            local retval, selected, muted, startppqpos, endppqpos, chan, pitch, vel = reaper.MIDI_GetNote(take, noteIndex)
            -- Check if the note start is within the time selection
            if startppqpos >= start_ppq and startppqpos < end_ppq and track_num > 0
                    and not ((hs and track_num == 1 and pitch == hksw)
                    or (ha and track_num == 2 and pitch == hksw)
                    or (ht and track_num == 3 and pitch == hksw)
                    or (hb and track_num == 4 and pitch == hksw))
            then
                reaper.MIDI_DeleteNote(take, noteIndex)
            end
        end
    end
end

function isValidFormat(str)
    -- Pattern to match the specific format of each line
    local pattern = "^%d+, %d+, %d+%.%d+, %d+%.%d+$"
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
    --print(response)
    local latestEnd = 0
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
        deleteNotesInTimeSelection(satbify_takes)
    end
    -- Loop through each line of the response body
    for line in response:gmatch("[^\r\n]+") do
        -- Parse the note data from the line
        local track_num, note_num, startTime, endTime = line:match("(%d+), (%d+), (%d+%.%d*), (%d+%.%d*)")
        -- Check if all required data was successfully parsed
        if track_num and note_num and startTime and endTime then
            -- Convert parsed data from string to numbers
            --print(endTime)
            track_num = tonumber(track_num)
            note_num = tonumber(note_num)
            startTime = tonumber(startTime)
            endTime = tonumber(endTime)
            -- Loop through the provided takes to find the correct track to insert the note
            for i, take_info in ipairs(takes) do
                local take = take_info[1] -- The take object
                local take_track_index = take_info[2] -- The track index
                local note_ppq_start = reaper.MIDI_GetPPQPosFromProjTime(take, startTime)
                local note_ppq_end =   reaper.MIDI_GetPPQPosFromProjTime(take, endTime)
                --print(note_ppq_end)
                -- If the take's track number matches the parsed track number, insert the note
                if take_track_index == track_num then
                    -- Insert the note into the MIDI take
                    reaper.MIDI_InsertNote(
                            take,
                            false, -- selected
                            false, -- muted
                            note_ppq_start, -- startppqpos
                            note_ppq_end,   -- endppqpos
                            0, -- channel
                            note_num, -- pitch
                            100, -- velocity
                            false) -- noSort
                end
                if endTime > latestEnd then
                    latestEnd = endTime
                end
            end
        elseif flagExit then -- Do not print anything if main track is created only now
        else
            -- Log an error message if parsing the line failed
            reaper.ShowMessageBox("I can't do this this just yet... ", "Satbify", 0)
        end
    end


    -- Sort MIDI data after inserting notes and make takes longer
    for _, take_info in ipairs(takes) do
        local take = take_info[1]
        local track_num = take_info[2] -- The track index
        reaper.MIDI_Sort(take)
        if track_num > 0 then
            local item = reaper.GetMediaItemTake_Item(take)
            local take_start_time = reaper.GetMediaItemInfo_Value(item, "D_POSITION")
            local take_length = reaper.GetMediaItemInfo_Value(item, "D_LENGTH")
            local take_end_time = take_start_time + take_length
            -- Calculate the new length needed
            local nudge_length = take_end_time - latestEnd
            -- Apply nudge to extend the take length
            if nudge_length < 0 then
                reaper.SetMediaItemSelected(item, true)
                reaper.ApplyNudge(0, 0, 3, 1, nudge_length, true, 0)
                reaper.SetMediaItemSelected(item, false)
            end
        end
    end
end

if not flagExit then
    parse_and_insert_notes(response_body, satbify_takes)
end
if sel_item_before then
    reaper.SetMediaItemSelected(sel_item_before, true)
end

reaper.Undo_EndBlock("Insert MIDI notes from HTTP response", -1)

-- label at last line
::exit::
