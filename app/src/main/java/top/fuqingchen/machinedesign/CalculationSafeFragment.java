package top.fuqingchen.machinedesign;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.solvers.AllowedSolution;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;
import org.apache.commons.math3.analysis.solvers.BracketingNthOrderBrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.apache.commons.math3.exception.NoBracketingException;

import androidx.fragment.app.Fragment;


/**
 * @author Fu_Qingchen
 */
public class CalculationSafeFragment extends Fragment {
    View view;
    private TextInputLayout input_function, input_low, input_up;
    private boolean flag = false;
    private String[] func = {"abs()", "acos()", "asin()", "atan()", "cbrt()", "ceil()", "cos()",
            "cosh()", "exp()", "floor()", "log()", "log10()", "log2()", "sin()", "sinh()", "sqrt()",
            "tan()", "tanh()", "signum()", "e", "pi"};
    MaterialCardView copy;
    double result;

    public CalculationSafeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calculation_safe, container, false);

        final AutoCompleteTextView function = view.findViewById(R.id.safe_function);
        final TextInputEditText low = view.findViewById(R.id.safe_low);
        final TextInputEditText up = view.findViewById(R.id.safe_up);
        input_function = view.findViewById(R.id.safe_input_function);
        input_low = view.findViewById(R.id.safe_input_low);
        input_up = view.findViewById(R.id.safe_input_up);
        TextView delete = view.findViewById(R.id.safe_delete);
        TextView doc = view.findViewById(R.id.safe_doc);
        copy = view.findViewById(R.id.safe_copy);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, func);
        function.setAdapter(arrayAdapter);

        up.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                View focusView = null;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(up.getText().toString())) {
                        input_up.setError(getString(R.string.app_unWrite));
                        focusView = up;
                        cancel = true;
                    }
                    if (TextUtils.isEmpty(low.getText().toString())) {
                        input_low.setError(getString(R.string.app_unWrite));
                        focusView = low;
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
                        input_low.setError(null);
                        input_up.setError(null);

                        String temFunction = function.getText().toString();
                        double temLow = Double.valueOf(low.getText().toString());
                        double temUp = Double.valueOf(up.getText().toString());

                        if (temLow >= temUp) {
                            input_low.setError(getString(R.string.app_input_error));
                            input_up.setError(getString(R.string.app_input_error));
                        } else {
                            result = solver(temFunction, temUp, temLow);
                            String input = getResources().getString(R.string.calculation_result) + ":\t" + result;

                            ((TextView) view.findViewById(R.id.safe_result)).setText(input);
                        }
                    }
                }
                return false;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_function.setError(null);
                input_low.setError(null);
                input_up.setError(null);
                function.getText().clear();
                low.getText().clear();
                up.getText().clear();
                flag = false;

                getFragmentManager()
                        .beginTransaction()
                        .detach(CalculationSafeFragment.this)
                        .attach(CalculationSafeFragment.this)
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
                    ClipData mClipData = ClipData.newPlainText("Result", result + "");
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

    private double solver(final String f, double top, double button) {
        UnivariateFunction function = new UnivariateFunction() {
            @Override
            public double value(double x) {
                try {
                    Expression e = new ExpressionBuilder(f)
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
        int number = 0;
        for (double i = button; i < top; i += (top - button) / 100) {
            if (function.value(i) == 0) {
                number++;
            }
        }
        if (number >= 10) {
            input_function.setError(getString(R.string.app_input_error));
            return 0;
        } else {
            Log.e("number", number + "");
            final double relativeAccuracy = 1.0e-12;
            final double absoluteAccuracy = 1.0e-8;
            final int maxOrder = 5;
            UnivariateSolver solver = new BracketingNthOrderBrentSolver(relativeAccuracy, absoluteAccuracy, maxOrder);
            try {
                return solver.solve(100, function, button, top);
            } catch (NoBracketingException e) {
                input_function.setError(getString(R.string.app_input_error));
                input_low.setError(getString(R.string.app_input_error));
                input_up.setError(getString(R.string.app_input_error));
                return 0;
            }
        }
    }
}
