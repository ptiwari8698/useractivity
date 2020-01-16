Steps to work with Application

Download Project from gitHub
import project as gradel project into STS
create db in mysql with db name as - `useractivity`
src/main/resources/db- sql file is there ,execute that sql file into your created db 
refresh gradel project which download all dependecies
run project as springBoot App
Create `D://EmployeeActivitesToBeProcessed` into your machine (System) 
keep all json file in that folder
Get-`localhost:8080/api/importData` hit GET url from postman (Whcich will read all file from folder and save in DB)

GET-`localhost:8080/api/viewReport` to See All Report 