# Simple task manager

<a href="https://play.google.com/store/apps/details?id=com.alexkaz.simplytaskmanager"><img src="http://www.android.com/images/brand/get_it_on_play_logo_large.png"/></a>

### Summary of application

This application for Android OS smartphones is designed to divide a long or difficult task into simple steps and monitor their implementation. Each task should be divided into simple steps. For the user step by step is psychologically easier to execute such task, and the statistics in  program is only motivate to complete the task 100%. Just divide something complex into simple steps and each step in carrying set the appropriate status. Data will be calculated and displayed in the form of various graphs and statistics that will show the effectiveness of implementation. Thus, the performance of any task will be easier and more interesting.

### Third-party libraries used in the application

Dependencies in Gradle

```groovy
dependencies {
    compile 'com.android.support:appcompat-v7:23.2.0'
    compile 'com.android.support.constraint:constraint-layout:1.0.0-alpha4'
    compile 'com.android.support:cardview-v7:23.2.0'
    compile 'com.android.support:design:23.2.0'
    compile 'com.sothree.slidinguppanel:library:3.3.0'
    compile 'com.nineoldandroids:library:2.4.0'
}
```

Libraries in libs folder from the site - https://github.com/nhaarman/ListViewAnimations
```
listviewanimations_lib-core_3.1.0
listviewanimations_lib-core-slh_3.1.0
listviewanimations_lib-manipulation_3.1.0
```

### Home screen of app with a list of tasks

At this screen displays a list of all tasks, and at the bottom is hidden drawer statistics.

![screen 1](/screenshots/first_screen.gif)

### The screen for full viewing a task

At this screen displays a list of steps, steps that make up the task. Each stage can change the status of implementation through dialogue choices. There is also sliding bottom panel that shows execution statistics.

![screen 2](/screenshots/second_screen.gif)

### The screen for creating a new task

This screen is to create a new task. It is possible to select the desired icon, enter the task name and stages of the task, editing screen looks the same. On error, you can go back to editing task at any time and fix it, delete some or add new items and so on.

![screen 3](/screenshots/third_screen.gif)

### Planned for the future

Currently the app only at the initial stage of its existence. In the future it is planned to improve and fill many new useful functions for managing workflow.
It is planned to provide the following innovations:
* daily tasks mode: users add tasks that need to perform every day and   app will be monitor the implementation and remind users about their performance.
* Mode 21 - day: this mode is designed to produce a particular habit, as we know that the habit is made 21 days. The program will help keep track of this and remind each day about the need for implementation.
* plans to create a flexible system of messages that user can customize to his desire
* plans to completely alter and improve the statistics. Add a nice informative charts and other means of displaying statistics that motivate the user to carry out its objectives.

