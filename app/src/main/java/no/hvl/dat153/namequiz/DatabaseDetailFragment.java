package no.hvl.dat153.namequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link DatabaseListActivity}
 * in two-pane mode (on tablets) or a {@link DatabaseDetailActivity}
 * on handsets.
 */
public class DatabaseDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DatabaseList.Person mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DatabaseDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DatabaseList.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
            FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(mItem);
                    moveToDatabaseActivity();

                }
            });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.database_detail, container, false);

        if (mItem != null) {
            ImageView im = ((ImageView) rootView.findViewById(R.id.database_detail));
            im.setImageBitmap(mItem.getImage());
        }

        return rootView;
    }
    private void moveToDatabaseActivity() {
        Intent intent = new Intent(this.getActivity(), DatabaseListActivity.class);
        startActivity(intent);
    }
    public void deleteItem(DatabaseList.Person item) {
        DatabaseList.ITEMS.remove(item);

    }
}
