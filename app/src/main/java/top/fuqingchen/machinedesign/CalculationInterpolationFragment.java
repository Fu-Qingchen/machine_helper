package top.fuqingchen.machinedesign;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author Fu_Qingchen
 */
public class CalculationInterpolationFragment extends Fragment {

    double number;
    boolean flag = false;
    private TextInputLayout input_x0, input_x1, input_y0, input_y1, input_x;


    public CalculationInterpolationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_calculation_interpolation, container, false);
        final AutoCompleteTextView x0 = view.findViewById(R.id.interpolation_x0);
        final AutoCompleteTextView x1 = view.findViewById(R.id.interpolation_x1);
        final AutoCompleteTextView y0 = view.findViewById(R.id.interpolation_y0);
        final AutoCompleteTextView y1 = view.findViewById(R.id.interpolation_y1);
        final AutoCompleteTextView x = view.findViewById(R.id.interpolation_x);
        input_x0 = view.findViewById(R.id.interpolation_input_x0);
        input_x1 = view.findViewById(R.id.interpolation_input_x1);
        input_y0 = view.findViewById(R.id.interpolation_input_y0);
        input_y1 = view.findViewById(R.id.interpolation_input_y1);
        input_x = view.findViewById(R.id.interpolation_input_x);
        CardView copy = view.findViewById(R.id.interpolation_copy);
        TextView delete = view.findViewById(R.id.interpolation_delete);

        x.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                View focusView = null;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(x.getText().toString())) {
                        input_x.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focusView = x;
                    }
                    if (TextUtils.isEmpty(y1.getText().toString())) {
                        input_y1.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focusView = y1;
                    }
                    if (TextUtils.isEmpty(y0.getText().toString())) {
                        input_y0.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focusView = y0;
                    }
                    if (TextUtils.isEmpty(x1.getText().toString())) {
                        input_x1.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focusView = x1;
                    }
                    if (TextUtils.isEmpty(x0.getText().toString())) {
                        input_x0.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focusView = x0;
                    }
                    if (cancel) {
                        focusView.requestFocus();
                    } else {
                        input_x0.setError(null);
                        input_x1.setError(null);
                        input_y0.setError(null);
                        input_y1.setError(null);
                        input_x.setError(null);
                        double temX0 = Double.valueOf(x0.getText().toString());
                        double temX1 = Double.valueOf(x1.getText().toString());
                        double temY0 = Double.valueOf(y0.getText().toString());
                        double temY1 = Double.valueOf(y1.getText().toString());
                        double temX = Double.valueOf(x.getText().toString());
                        double[][] data = {{temX0, temX1}, {temY0, temY1}};

                        InterpolationInMath interpolationInMath = new InterpolationInMath();
                        interpolationInMath.setData(data);
                        interpolationInMath.setNumber(temX);

                        number = interpolationInMath.getNumber();
                        String input = getResources().getString(R.string.calculation_result) + "\t" + number;
                        ((TextView) view.findViewById(R.id.interpolation_result)).setText(input);
                        flag = true;
                    }
                }
                return false;
            }
        });

        delete.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                input_x0.setError(null);
                input_x1.setError(null);
                input_y0.setError(null);
                input_y1.setError(null);
                input_x.setError(null);
                x0.getText().clear();
                x1.getText().clear();
                y0.getText().clear();
                y1.getText().clear();
                x.getText().clear();
                flag = false;

                getFragmentManager()
                        .beginTransaction()
                        .detach(CalculationInterpolationFragment.this)
                        .attach(CalculationInterpolationFragment.this)
                        .commit();
            }
        });

        copy.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (flag) {
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", number + "");
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), R.string.copy_done, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.copy_false, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
