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

    private static final String ARG_SEARCH = "search";

    private ArrayList<InventoryItem> mInventoryList;
    private RecyclerView mRecycler;

//    private OnFragmentInteractionListener mListener;

    public SearchFragment() {}

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

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
