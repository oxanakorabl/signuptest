# List101

### Intro 
To start using a project first install JDK 8.
In app settings (File-Project structure) set using external JDK and write path to it.
Likewise set `JAVA_HOME`, `ANDROID_HOME` system variables. And add needed paths to `PATH` variable.

Example:
```
JAVA_HOME - C:\Program Files\Java\jdk1.8.0_121
ANDROID_HOME - C:\Android\sdk
PATH - C:\Android\sdk\platform-tools\;C:\Android\sdk\tools\;%JAVA_HOME%\bin;
```

#### Static analysis
Also our project has "staticAnalysis" Gradle task that should be executed prior to commiting to the repo. 
This task could be strted from command line with shortcut "SA" (`gradlew SA`)

#### Git Commit message’s format:
***First line of commit***
- **Type of change** - Bugfix, Change, Feature_Product_backlog_number_Name_of_item: Makes this.

Example:
```
Bugfix: Fixes dummy error.
Feature_3638_Search: Makes this.
Change: Swappes one with another.
```

#### Code agreements

1. In code we use one-line comments (//) everywhere, except creating JAVA Doc.
2. Likewise please use annotations from `android.support.annotation`.
3. To have consistent codebase try to use these Android Studio shortcuts for java classes and xml files:
    1. Use "Reformat code"	Control + Alt + L
    2. Optimize imports	Control + Alt + O
    3. Set in IDE "Settings->Editor->Code Style->Java->Code Generation" generating final modifiers for local variables and parameters. 
    


##### Java class structure:
```
// -- Constants
// -- Static functions
// -- Variables
// -- Abstract functions
// -- Construction
// -- Functions
// -- Actions
// -- Private functions
// -- Inner types
Also you can add marks like this, to group methods.
// -- Functions: MAIL HANDLERS
```

***Drawable's names***
- **bg_green**
- **bg_credits**
- **bg_screen_item_medium.xml**
- **btn_filter – (can be selector with image & state)**
- **ic_favorite_ – (can be selector with image & state)**
- **ic_rect_white_with_border**
- **bg_primary_rounded**
- **btn_arrow_crimson**
- **switch_light**
- **shape_dark**
- **vector_user_profile_logo**

***Image's names***
- **btn_loupe_pressed**
- **btn_loupe_normal**
- **btn_loupe_disabled.png**
- **ic_pause**
- **btn_check**
- **btn_radio**
- **switch_bg**
- **text_header**
- **bg_dashboard**
- **btn_map_disabled**

***Layouts***
- **fragment_wallet**
- **fragment_voucher_list**
- **dialog_fragment_warning**
- **checkbox_data**
- **drawer_item**
- **activity_root**
- **merge_divider**
- **include_contact_info**
- **item_some_list**
- **widget_toolbar.xml**

***IDs - Start id wit prefix of component (which's inherited from layout file name)***
- ***[layout_type]_[exact_name]***
    - **frame_layout_blocked_message**
    - **linear_layout_root**
    - **layout_root**
    - **container_main**
    - **label_total_available**
- **textview_overdue_message**
- **progressbar_user_update**
- **button_show_info**
- **widget_search**
- **edittext_mame**
- **imageview_user**
- **viewpager_products**
- **view_left_space**
- **list_vouchers**
- **checkbox_point_type**

***Menu***
- **menu_catalog**


***View's variables names***
- **TextView - someTextView**
- **Button, ImageButton or any clickable element - could be someButton;**
- **Layouts - someFrameLayout, someLinearLayout, someLayout**
- **Custom view - someView or someWidget;**
- **EditText - someEditText;**
