
-- Function to get the path of this script
function getScriptPath()
    local info = debug.getinfo(1, "S")
    return info.source:match("^@(.+)[/\\].-")
end

-- Function to get user input
function getUserInput()
    local retval, user_input = reaper.GetUserInputs("Save Text", 1, "Enter your text:", "")
    if retval and user_input ~= "" then
        saveToFile(user_input, getScriptPath() .. "/input_text.txt")
    else
        reaper.ShowMessageBox("No input provided!", "Error", 0)
    end
end

-- Function to save text to a file
function saveToFile(text, path)
    local file = io.open(path, "w")
    if file then
        file:write(text)
        file:close()
        reaper.ShowMessageBox("Text saved successfully!", "Success", 0)
    else
        reaper.ShowMessageBox("Failed to write to file!", "Error", 0)
    end
end

-- Run the script
getUserInput()
