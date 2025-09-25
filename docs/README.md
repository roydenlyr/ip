Shinchan User Guide
---
## Quick Start
1. Ensure you have Java 17 or above installed.
2. Download the latest ```ip.jar``` file.
3. Open a terminal, navigate (```cd```) to the folder containing the ```.jar```, and run:
```
java -jar ip.jar
```
4. You should see the Shinchan greeting.
5. Type a command and press **Enter** to execute it.

---
## Features
### 1. Adding tasks
**1.1 Todo:** ```todo``` 

Adds a task without a date.

**Format:**
```
todo <description>
```

**1.2 Deadline:** ```deadline```

Adds a task with a due date/time.

**Format:**
```
deadline <description> /by <yyyy-MM-dd HHmm>
```

**1.3 Event:** ```event```

Adds a task with a start and end date/time.

**Format:**
```
event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>
```
---
### 2. Listing all tasks: ```list```
Shows all tasks in the task list.

**Format:**
```
list
```
---
### 3. Marking / Unmarking tasks
Marks a task as done or not done.

**Format:**
```
mark <index>
unmark <index>
```
where index is the number shown beside each task in ```list```

---
### 4. Deleting a task: ```delete```
Deletes a task by its index in the list.

**Format:**
```
delete <index>
```

---
### 5. Finding tasks
**5.1 Find by keyword:** ```find```

**Format:**
```
find <keyword>
```

**5.2 Find by date:** ```date```

**Format:**
```
date <yyyy-MM-dd HHmm>
```

---
### 6. Exiting the program: ```bye```
Closes the ShinChan chatbot.

**Format:**
```
bye
```
---

## Saving Data
- Tasks are **saved automatically** to a text file after every change.
- They will be loaded back when you restart ShinChan.
---

## Command Summary
| Action       | Format / Example                                          |
|--------------|-----------------------------------------------------------|
| Add Todo     | `todo read book`                                          |
| Add Deadline | `deadline submit report /by 2025-09-30 2359`              |
| Add Event    | `event meeting /from 2025-09-29 1000 /to 2025-09-29 1200` |
| List         | `list`                                                    |
| Mark         | `mark 2`                                                  |
| Unmark       | `unmark 2`                                                |
| Delete       | `delete 3`                                                |
| Find Keyword | `find book`                                               |
| Find Date    | `date 2025-09-30 1800`                                    |
| Exit         | `bye`                                                     |
