# Android_SearchBar
A custom search bar that draws entirely from the canvas,runs on android studio.
Click [here](https://github.com/sshiqiao/iOS_SearchBar) for iOS version.

<pre>
&lt;com.start.searchbardemo.view.SearchBar
        android:id="@+id/search_bar_custom"
        android:layout_marginTop="50dp"
        android:layout_alignParentRight="true"
        android:layout_marginRight="50dp"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:hint="@string/app_name"
        searchBar:searchbar_background_color="@color/colorWhite"
        searchBar:searchbar_edge_color="@color/colorPrimaryDark"
        searchBar:searchbar_searching_color="@color/colorAccent"
        searchBar:searchbar_stretch="true"
        searchBar:searchbar_stretch_direction="0"
        searchBar:searchbar_stretch_width="250dp"/&gt;
</pre>

<pre>
&lt;com.start.searchbardemo.view.SearchBar
        android:id="@+id/search_bar_default"
        android:layout_marginTop="150dp"
        android:layout_marginLeft="50dp"
        android:layout_width="50dp"
        android:layout_height="50dp"
        searchBar:searchbar_stretch_direction="1"
        android:hint="@string/app_name"/&gt;
</pre>


![image](https://github.com/sshiqiao/Android_SearchBar/blob/master/app/src/main/res/demonstration.gif)
