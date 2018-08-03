package top.fuqingchen.machinedesign;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.FiniteDifferencesDifferentiator;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;

import androidx.fragment.app.Fragment;


/**
 * @author Fu_Qingchen
 */
public class CalculationDifferentiationFragment extends Fragment {
    private final int order = 3;
    private TextInputLayout input_function, input_x;
    private boolean flag = false;
    private double[] y_differentiated = new double[order + 1];
    private String[] func = {"abs()", "acos()", "asin()", "atan()", "cbrt()", "ceil()", "cos()",
            "cosh()", "exp()", "floor()", "log()", "log10()", "log2()", "sin()", "sinh()", "sqrt()",
            "tan()", "tanh()", "signum()", "e", "pi"};


    public CalculationDifferentiationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_calculation_differentiation, container, false);

        final AutoCompleteTextView function = view.findViewById(R.id.differentiation_function);
        final TextInputEditText x = view.findViewById(R.id.differentiation_x);
        input_function = view.findViewById(R.id.differentiation_input_function);
        input_x = view.findViewById(R.id.differentiation_input_x);
        TextView delete = view.findViewById(R.id.differentiation_delete);
        TextView doc = view.findViewById(R.id.differentiation_doc);
        final TextView copyY0 = view.findViewById(R.id.differentiation_result_y0);
        final TextView copyY1 = view.findViewById(R.id.differentiation_result_y1);
        final TextView copyY2 = view.findViewById(R.id.differentiation_result_y2);
        final TextView copyY3 = view.findViewById(R.id.differentiation_result_y3);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, func);
        function.setAdapter(arrayAdapter);

        x.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                View focusView = null;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(x.getText().toString())) {
                        input_x.setError(getString(R.string.app_unWrite));
                        focusView = x;
                        cancel = true;
                    }
                    if (TextUtils.isEmpty(function.getText().toString())) {
                        input_function.setError(getString(R.string.app_unWrite));
                        focusView = function;
                        cancel = true;
                    }
                    if (cancel) {
                        focusView.requestFocus();
                    } else {
                        flag = true;
                        input_function.setError(null);
                        input_x.setError(null);

                        String temFunction = function.getText().toString();
                        double temX = Double.valueOf(x.getText().toString());

                        if (temX >= 1000000) {
                            input_x.setError(getString(R.string.differentiation_x_tooMuch));
                        }
                        differentiation(temFunction, temX);

                        String inputy0 = getResources().getString(R.string.differentiation_y0) + ":\t" + y_differentiated[0];
                        String inputy1 = getResources().getString(R.string.differentiation_y1) + ":\t" + y_differentiated[1];
                        String inputy2 = getResources().getString(R.string.differentiation_y2) + ":\t" + y_differentiated[2];
                        String inputy3 = getResources().getString(R.string.differentiation_y3) + ":\t" + y_differentiated[3];

                        copyY0.setText(inputy0);
                        copyY1.setText(inputy1);
                        copyY2.setText(inputy2);
                        copyY3.setText(inputy3);
                    }
                }
                return false;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_function.setError(null);
                input_x.setError(null);
                function.getText().clear();
                x.getText().clear();
                flag = false;

                getFragmentManager()
                        .beginTransaction()
                        .detach(CalculationDifferentiationFragment.this)
                        .attach(CalculationDifferentiationFragment.this)
                        .commit();
            }
        });

        copyY0.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (flag) {
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", y_differentiated[0] + "");
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), R.string.copy_done, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.copy_false, Toast.LENGTH_SHORT).show();
                }
            }
        });
        copyY1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (flag) {
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", y_differentiated[1] + "");
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), R.string.copy_done, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.copy_false, Toast.LENGTH_SHORT).show();
                }
            }
        });
        copyY2.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (flag) {
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", y_differentiated[2] + "");
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), R.string.copy_done, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.copy_false, Toast.LENGTH_SHORT).show();
                }
            }
        });
        copyY3.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (flag) {
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", y_differentiated[3] + "");
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), R.string.copy_done, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.copy_false, Toast.LENGTH_SHORT).show();
                }
            }
        });

        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://fu-qingchen.github.io/HelloWorld/"));
                startActivity(intent);
            }
        });

        return view;
    }

    private void differentiation(final String function, double x) {
        UnivariateFunction basicF = new UnivariateFunction() {
            @Override
            public double value(double x) {
                try {
                    Expression e = new ExpressionBuilder(function)
                            .variable("x")
                            .build()
                            .setVariable("x", x);
                    input_function.setError(null);
                    return e.evaluate();
                } catch (IllegalArgumentException e) {
                    input_function.setError(getString(R.string.app_input_error));
                    return 0;
                }
            }
        };

        FiniteDifferencesDifferentiator differentiator =
                new FiniteDifferencesDifferentiator(5, 0.01);

        UnivariateDifferentiableFunction completeF = differentiator.differentiate(basicF);

        DerivativeStructure xx = new DerivativeStructure(1, order, 0, x);
        DerivativeStructure y = completeF.value(xx);
        y_differentiated[0] = y.getValue();
        y_differentiated[1] = y.getPartialDerivative(1);
        y_differentiated[2] = y.getPartialDerivative(2);
        y_differentiated[3] = y.getPartialDerivative(3);
    }
}
