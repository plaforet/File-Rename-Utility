# File Rename Utility
Rename utility that will rename student photos based on a CSV pulled from a Google Form.

## Intro
This was designed to assist our graphic design teacher with student photos. Students would sign in to a Google Form with their ID number, then take their picture. Once all pictures were taken, a VLOOKUP formula would populate their Last Name and First Names, then the responses would be saved as a CSV.

This app is hardcoded for the known formatting of the CSV. The CSV will have columns for Timestamp, Last Name, First Name, and Student ID, in that order. Photos needed a similar naming convention: lastName, firstName, StudentID. The timestamp was important too, however. The first image should correlate with the first timestamped entry, etc.

When the app is launched, the user will have three buttons. The user must select the pirectory the photos are located in and the location for the CSV file before clicking Rename.

## TODO
There are some logic errors I need to resolve first, but once finished, I'd like to add the option of creating copies of the pictures with the new name rather than renaming the existing ones. I'd also like some variability in the naming scheme for the files, as well as some more flexibility in reading the CSV file.
