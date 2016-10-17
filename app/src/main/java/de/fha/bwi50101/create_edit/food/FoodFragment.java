package de.fha.bwi50101.create_edit.food;

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
import de.fha.bwi50101.create_edit.impl.FoodFragmentPresenterImpl;
import de.fha.bwi50101.create_edit.slider.LumindSlider;

/**
 * Created by Florian on 09.10.2016.
 */

public class FoodFragment extends Fragment implements FoodFragmentPresenter.View {
    @BindView(R.id.food_slider)
    LumindSlider slider;
    @BindView(R.id.food_reset)
    Button resetButton;
    private FoodFragmentPresenter presenter;

    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FoodFragmentPresenterImpl(((EntryProvider) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this, view);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resetClicked();
            }
        });
        presenter.setView(this);
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
        ((DisableableViewPagerHolder) getActivity()).disableViewPaging();
    }

    @Override
    public int getSliderColor() {
        return ContextCompat.getColor(this.getContext(), R.color.colorPrimary);
    }
}
