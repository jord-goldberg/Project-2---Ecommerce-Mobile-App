package villainyinc.schemedreams;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private static final String ARG_SEARCH = "searchResult";

    private ArrayList<InventoryItem> mInventoryList;
    private RecyclerView mRecycler;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(ArrayList<String> listOfSkus) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARG_SEARCH, listOfSkus);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.search_recycler);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        DBHelper db = DBHelper.getInstance(this.getContext());
        mInventoryList = new ArrayList<>();
        for (String sku : getArguments().getStringArrayList(ARG_SEARCH)) {
            mInventoryList.add(db.getItemFromSku(sku));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),
                LinearLayoutManager.VERTICAL, false);
        LongCardRecyclerAdapter cardRecyclerAdapter = new LongCardRecyclerAdapter(mInventoryList);
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(cardRecyclerAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}