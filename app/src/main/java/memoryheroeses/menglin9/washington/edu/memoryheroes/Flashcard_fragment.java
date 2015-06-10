package memoryheroeses.menglin9.washington.edu.memoryheroes;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Flashcard_fragment extends Fragment {

    memoryApp mApp;

    public Flashcard_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View vi = inflater.inflate(R.layout.fragment_flashcard_fragment, container, false);

        mApp = (memoryApp) getActivity().getApplication();
        TextView front = (TextView) vi.findViewById(R.id.front);
        front.setText(mApp.getCurrentFront());
        return vi;

    }

    }



