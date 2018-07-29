package top.fuqingchen.machinedesign;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;

/**
 * @author Fu_Qingchen
 */
public class CalculationIntegrationFragment extends Fragment {
    double number;
    boolean flag = false;
    private TextInputLayout input_function, input_low, input_up;
    private String[] func = {"abs()", "acos()", "asin()", "atan()", "cbrt()", "ceil()", "cos()",
            "cosh()", "exp()", "floor()", "log()", "log10()", "log2()", "sin()", "sinh()", "sqrt()",
            "tan()", "tanh()", "signum()","e","pi"};


    public CalculationIntegrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_calculation_integration, container, false);
        final AutoCompleteTextView function = view.findViewById(R.id.integration_function);
        final AutoCompleteTextView low = view.findViewById(R.id.integration_low);
        final AutoCompleteTextView up = view.findViewById(R.id.integration_up);
        input_function = view.findViewById(R.id.integration_input_function);
        input_low = view.findViewById(R.id.integration_input_low);
        input_up = view.findViewById(R.id.integration_input_up);
        CardView copy = view.findViewById(R.id.integration_copy);
        TextView delete = view.findViewById(R.id.integration_delete);
        TextView doc = view.findViewById(R.id.integration_doc);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,func);
        function.setAdapter(arrayAdapter);

        up.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                View focusView = null;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(up.getText().toString())) {
                        input_up.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focusView = up;
                    }
                    if (TextUtils.isEmpty(low.getText().toString())) {
                        input_low.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focusView = low;
                    }
                    if (TextUtils.isEmpty(function.getText().toString())) {
                        input_function.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focusView = function;
                    }
                    if (cancel) {
                        focusView.requestFocus();
                    } else {
                        input_function.setError(null);
                        input_low.setError(null);
                        input_up.setError(null);
                        String temFunction = function.getText().toString();
                        double temLow = Double.valueOf(low.getText().toString());
                        double temUp = Double.valueOf(up.getText().toString());

                        flag = true;

                        if (temLow > temUp) {
                            input_low.setError(getString(R.string.app_input_error));
                            input_up.setError(getString(R.string.app_input_error));
                            flag = false;
                        } else {
                            input_low.setError(null);
                            input_up.setError(null);
                            if (temLow < temUp) {
                                number = integration(temFunction, temLow, temUp);
                            } else if (temLow == temUp) {
                                number = 0;
                            }
                            String input = getResources().getString(R.string.calculation_result) + "\t" + number;
                            ((TextView) view.findViewById(R.id.integration_result)).setText(input);
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
                        .detach(CalculationIntegrationFragment.this)
                        .attach(CalculationIntegrationFragment.this)
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

    private double integration(final String f, double a, double b) {
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
        UnivariateIntegrator univariateIntegrator = new SimpsonIntegrator();
        return univariateIntegrator.integrate(10000, function, a, b);
    }
}
