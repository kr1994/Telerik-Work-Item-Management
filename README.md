<img src="https://webassets.telerikacademy.com/images/default-source/logos/telerik-academy.svg)" alt="logo" width="300px" style="margin-top: 20px;"/>

## OOP Teamwork Assignment Work Item Management (WIM) Console application


There are two lists teams and people in the repo.

A Team has unique **name** (5-15$), **members** and **boards**

A Member has unique **name** (5-15$), **list of WIs** and **activity history**

A Board has unique **name** (5-15$), **list of WIs** and **active history**

There are 3 types of WIs: **Bug**, **Story** and **FeedBack** (FB).

All 3 have in comon:

- unique ID
- Title (10-50$)
- Description (10-500$)
- List of Comments (list of (contend,author))
- List of History (list of changes)
+ Status - can be abstract because has diferent values

FB has also:

- Rating (Integer)
+ Status (New, Unscheduled, Scheduled or Done)

Bugs and Storyes have in comon:

- Priority (High, Medium or Low)
- Assignee (person who is doing it)

Bugs have also:

- Steps to reproduce (String list)
- Severity (Critical, Major or Minor)
+ Status is (Active or Fixed)
 
Story has also:

- Size (Large, Medium or Small)
+ Status is (NotDone, InProgress or Done)


### Design the Class Hierarchy

We can make an abstract WorkItemBase class;
FB and abstract TaskBase can extend WorkItemBase;
and Bug and Story can extend TaskBase.

WorkItemBase --> TaskBase--> Story <br>
==|============|=============<br>
==V===========V=============<br>
 Feedback=|=|=|=|=Bugs


### Validation

**Each field that must be validated before assignment**

- String validation:
  -not null and in borders.
**All properties in the interfaces are mandatory (cannot be null or empty).
    If a null value is passed to some mandatory property, 
your program should throw a proper exception.**

### User actions

- Create new person
- Create new team<br>
====
- Add person to a team
- Assign/Unassign work to a person<br>
====
- Create new bord in a team
- Create any task in a board<br>
====
- Show all people
- Show person's activity<br>
====
- Show all teams
- Show team's activity
- Show all team's members
- Show all team's boards<br>
=====
- Show all Board's activity<br>
=====
- Change each ("enum field" in any task, and Rating in FB)
- Add comment to WI

### List work items whit options:
- List all WI
- List any WI only
- Filter WI by status
- Filter WI by assignee
- Filter WI by status and assignee
- Sort WI by Title/ Priority/ Severity/ Size/ Rating

#### Additional Notes:
- Use Streaming API whenever you can instead of for loops
- Use meaningful exeption messages
- Cover more than 80% of the code whit unit tests
