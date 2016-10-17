package de.fha.bwi50101.create_edit.insulin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.DisableableViewPagerHolder;
import de.fha.bwi50101.create_edit.EntryProvider;
import de.fha.bwi50101.create_edit.impl.InsulinFragmentPresenterImpl;
import de.fha.bwi50101.create_edit.slider.LumindSlider;

/**
 * Created by Florian on 09.10.2016.
 */

public class InsulinFragment extends Fragment implements InsulinFragmentPresenter.View {
    @BindView(R.id.insulin_slider)
    LumindSlider slider;
    @BindView(R.id.insulin_reset)
    Button resetButton;
    private InsulinFragmentPresenter presenter;

    public static InsulinFragment newInstance() {
        return new InsulinFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new InsulinFragmentPresenterImpl(((EntryProvider) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insulin, container, false);
        ButterKnife.bind(this, view);
        presenter.setView(this);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resetClicked();
            }
        });
        return view;

    }

    @Override
    public LumindSlider getSlider() {
        return slider;
    }

    @Override
    public void slidingStarted() {
        ((DisableableViewPagerHolder) getActivity()).disableViewPaging();
    }

    @Override
    public void slidingStopped() {
        ((DisableableViewPagerHolder) getActivity()).enableViewPaging();
    }

    @Override
    public int getSliderColor() {
        return ContextCompat.getColor(getContext(), R.color.colorPrimary);
    }
}
