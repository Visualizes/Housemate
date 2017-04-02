package com.visual.android.housemate;

import android.widget.EditText;

/**
 * Created by RamiK on 3/31/2017.
 */

public class Singleton {

    public SearchEditText mSearch;

    public void setSearch(SearchEditText mSearch) {
        this.mSearch = mSearch;
    }

    public SearchEditText getSearch() {
        return mSearch;
    }
}
