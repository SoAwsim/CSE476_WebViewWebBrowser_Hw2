# Yeditepe University CSE 476 Web View Assignment Assignment 2

In this assignment we were expected to implement a simple web browser using Android's builtin Web View.
In addition to the core features I have implemented unlimited tab logic by implementing Android Life Cycle
by implementing parcelable interface for my classes.

There are small bugs when device screen is rotated where tabs can get mixed up but that out of the
scope of the assignment so I did not spend time fixing it.

To close tabs you can press the name of the tab.

As for the architecture I was trying every type of implementation of Android Architecture in my assignments
and in this assignment I have experimented with the parcelable interface.

## Features
- Has Internet access and can browse through web pages.
- Has easy to use layout with buttons, such as Go!, to open the web page given on the address bar;
- Gives the user the possibility to browse through multiple web sites 
simultaneously by the help of tabs – your application should start with only
one tab, but by clicking on the New Tab button users should be able to
dynamically create at least 10 browser tabs (by considering device memory
capacity) during run time - unlimited (bonus);
- Current web page’s icon (if any) and the title should be shown on the tab
handle;
- In the case where selected tab changed the address bar should also change
accordingly.

## Running
Clone the repo:
```bash
git clone https://github.com/SoAwsim/CSE476_WebViewWebBrowser_Hw2
```
Then build using Android Studio.
