persontracker
=============

#Get an AR drone to follow a target or person.

The plan is to have a quad-copter drone hover at a set position
from an AR target.  A person could wear this target or we could
put it on something moving.

At this point in the project, we only have a minimal Android
app that allows the drone to take off vertically and then land
back down again.

It has been difficult to get the SDK provided by Parrot to do
what we want---getting a video feed off the drone and into an
app has been tricky.

And there is currently no AR target tracker implemented.  There
is a face detector which could provide input to a navigation
system for the drone, but it is not actually implemented at
the moment.

#TODO

* Consider refactoring the native SDK to be easier to use,
possibly an all-Java solution would make more sense on Android

* Find an image tracking library that can take a feed from the
drone and locate an AR target

