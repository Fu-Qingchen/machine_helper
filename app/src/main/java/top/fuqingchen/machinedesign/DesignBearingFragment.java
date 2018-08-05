package top.fuqingchen.machinedesign;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;


/**
 * @author Fu_Qingchen
 */
public class DesignBearingFragment extends Fragment {
    private View view, focus = null;
    private boolean flag = false, flag_iFAC0r = false, flag_FAFR = false, finish = false;
    TextInputEditText bearing_FR, bearing_FA, bearing_i, bearing_Cr, bearing_C0r, bearing_e,
            bearing_fp, bearing_X, bearing_Y, bearing_ft, bearing_n;
    TextInputLayout bearing_FR_input, bearing_FA_input, bearing_i_input, bearing_Cr_input,
            bearing_C0r_input, bearing_e_input, bearing_fp_input,
            bearing_X_input, bearing_Y_input, bearing_ft_input, bearing_n_input;
    private double FR, FA, Cr, FAFR, iFAC0r, P, L, Lh;
    RadioGroup bearing_style;
    RadioButton bearing_r, bearing_l;
    double type = 1;
    private boolean clear = false;


    public DesignBearingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_design_bearing, container, false);

        bearing_FR = view.findViewById(R.id.bearing_FR);
        bearing_FA = view.findViewById(R.id.bearing_FA);
        bearing_i = view.findViewById(R.id.bearing_i);
        bearing_Cr = view.findViewById(R.id.bearing_Cr);
        bearing_C0r = view.findViewById(R.id.bearing_C0r);
        bearing_e = view.findViewById(R.id.bearing_e);
        bearing_fp = view.findViewById(R.id.bearing_fp);
        bearing_X = view.findViewById(R.id.bearing_X);
        bearing_Y = view.findViewById(R.id.bearing_Y);
        bearing_ft = view.findViewById(R.id.bearing_ft);
        bearing_n = view.findViewById(R.id.bearing_n);

        bearing_FR_input = view.findViewById(R.id.bearing_FR_input);
        bearing_FA_input = view.findViewById(R.id.bearing_FA_input);
        bearing_i_input = view.findViewById(R.id.bearing_i_input);
        bearing_Cr_input = view.findViewById(R.id.bearing_Cr_input);
        bearing_C0r_input = view.findViewById(R.id.bearing_C0r_input);
        bearing_e_input = view.findViewById(R.id.bearing_e_input);
        bearing_fp_input = view.findViewById(R.id.bearing_fp_input);
        bearing_X_input = view.findViewById(R.id.bearing_X_input);
        bearing_Y_input = view.findViewById(R.id.bearing_Y_input);
        bearing_ft_input = view.findViewById(R.id.bearing_ft_input);
        bearing_n_input = view.findViewById(R.id.bearing_n_input);

        bearing_style = view.findViewById(R.id.bearing_style);
        bearing_r = view.findViewById(R.id.bearing_r);
        bearing_l = view.findViewById(R.id.bearing_l);

        TextView delete = view.findViewById(R.id.bearing_delete);
        TextView copy = view.findViewById(R.id.bearing_copy);

        bearing_style.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                boolean vclear = clear;
                Log.e("clear2", clear + "");
                Log.e("vclear2", vclear + "");
                if (!vclear) {
                    Log.e("clear3", clear + "");
                    Log.e("vclear3", vclear + "");
                    RadioButton radioButton = view.findViewById(checkedId);
                    switch (radioButton.getText().toString()) {
                        case "球轴承":
                            type = 3;
                            break;
                        case "滚子轴承":
                            type = 10.0 / 3;
                            break;
                        default:
                            type = 0;
                    }
                    if (finish) {
                        double ft = Double.valueOf(bearing_ft.getText().toString());
                        double n = Double.valueOf(bearing_n.getText().toString());

                        String L = getResources().getString(R.string.bearing_L) + ":\t" + getL(Cr, ft, P, type);
                        String Lh = getResources().getString(R.string.bearing_Lh) + ":\t" + getLh(n, Cr, ft, P, type);

                        ((TextView) view.findViewById(R.id.bearing_L)).setText(L);
                        ((TextView) view.findViewById(R.id.bearing_Lh)).setText(Lh);
                    }
                }
            }
        });

        bearing_C0r.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                clear = false;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(bearing_C0r.getText().toString())) {
                        bearing_C0r_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_C0r;
                    }
                    if (TextUtils.isEmpty(bearing_Cr.getText().toString())) {
                        bearing_Cr_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_Cr;
                    }
                    if (TextUtils.isEmpty(bearing_i.getText().toString())) {
                        bearing_i_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_i;
                    }
                    if (TextUtils.isEmpty(bearing_FA.getText().toString())) {
                        bearing_FA_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_FA;
                    }
                    if (TextUtils.isEmpty(bearing_FR.getText().toString())) {
                        bearing_FR_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_FR;
                    }
                    if (cancel) {
                        focus.requestFocus();
                    } else {
                        FA = Double.valueOf(bearing_FA.getText().toString());
                        FR = Double.valueOf(bearing_FR.getText().toString());
                        Cr = Double.valueOf(bearing_Cr.getText().toString());
                        int ii = Integer.valueOf(bearing_i.getText().toString());
                        double C0r = Double.valueOf(bearing_C0r.getText().toString());

                        bearing_e_input.setHelperText("iFA/C0r=" + getIFAC0r(ii, FA, C0r));
                        bearing_X_input.setHelperText("FA/FR=" + getFAFR(FA, FR));
                        bearing_Y_input.setHelperText("FA/FR=" + getFAFR(FA, FR));

                        bearing_FA_input.setError(null);
                        bearing_FR_input.setError(null);
                        bearing_i_input.setError(null);
                        bearing_Cr_input.setError(null);
                        bearing_C0r_input.setError(null);

                        focus = bearing_e;
                        focus.requestFocus();
                    }
                }
                return false;
            }
        });

        bearing_e_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag_iFAC0r) {
                    clear = false;
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", iFAC0r + "");
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), "iFA/C0r复制完成", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bearing_X_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag_FAFR) {
                    clear = false;
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", FAFR + "");
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), "FA/FR复制完成", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bearing_Y_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag_FAFR) {
                    clear = false;
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", FAFR + "");
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), "FA/FR复制完成", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bearing_Y.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                clear = false;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(bearing_Y.getText().toString())) {
                        bearing_Y_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_Y;
                    }
                    if (TextUtils.isEmpty(bearing_X.getText().toString())) {
                        bearing_X_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_X;
                    }
                    if (TextUtils.isEmpty(bearing_fp.getText().toString())) {
                        bearing_fp_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_fp;
                    }
                    if (TextUtils.isEmpty(bearing_e.getText().toString())) {
                        bearing_e_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_e;
                    }
                    if (TextUtils.isEmpty(bearing_C0r.getText().toString())) {
                        bearing_C0r_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_C0r;
                    }
                    if (TextUtils.isEmpty(bearing_Cr.getText().toString())) {
                        bearing_Cr_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_Cr;
                    }
                    if (TextUtils.isEmpty(bearing_i.getText().toString())) {
                        bearing_i_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_i;
                    }
                    if (TextUtils.isEmpty(bearing_FA.getText().toString())) {
                        bearing_FA_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_FA;
                    }
                    if (TextUtils.isEmpty(bearing_FR.getText().toString())) {
                        bearing_FR_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_FR;
                    }
                    if (cancel) {
                        focus.requestFocus();
                    } else {
                        bearing_FR_input.setError(null);
                        bearing_FA_input.setError(null);
                        bearing_i_input.setError(null);
                        bearing_Cr_input.setError(null);
                        bearing_C0r_input.setError(null);
                        bearing_e_input.setError(null);
                        bearing_fp_input.setError(null);
                        bearing_X_input.setError(null);
                        bearing_Y_input.setError(null);

                        double X = Double.valueOf(bearing_X.getText().toString());
                        double Y = Double.valueOf(bearing_Y.getText().toString());

                        String p = getResources().getString(R.string.bearing_P) + ":\t" + getP(X, FR, Y, FA);

                        ((TextView) view.findViewById(R.id.bearing_P)).setText(p);

                        focus = bearing_ft;
                        focus.requestFocus();
                    }
                }
                return false;
            }
        });

        bearing_n.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                clear = false;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(bearing_n.getText().toString())) {
                        bearing_n_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_n;
                    }
                    if (TextUtils.isEmpty(bearing_ft.getText().toString())) {
                        bearing_ft_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_ft;
                    }
                    if (TextUtils.isEmpty(bearing_Y.getText().toString())) {
                        bearing_Y_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_Y;
                    }
                    if (TextUtils.isEmpty(bearing_X.getText().toString())) {
                        bearing_X_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_X;
                    }
                    if (TextUtils.isEmpty(bearing_fp.getText().toString())) {
                        bearing_fp_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_fp;
                    }
                    if (TextUtils.isEmpty(bearing_e.getText().toString())) {
                        bearing_e_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_e;
                    }
                    if (TextUtils.isEmpty(bearing_C0r.getText().toString())) {
                        bearing_C0r_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_C0r;
                    }
                    if (TextUtils.isEmpty(bearing_Cr.getText().toString())) {
                        bearing_Cr_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_Cr;
                    }
                    if (TextUtils.isEmpty(bearing_i.getText().toString())) {
                        bearing_i_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_i;
                    }
                    if (TextUtils.isEmpty(bearing_FA.getText().toString())) {
                        bearing_FA_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_FA;
                    }
                    if (TextUtils.isEmpty(bearing_FR.getText().toString())) {
                        bearing_FR_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = bearing_FR;
                    }
                    if (cancel) {
                        focus.requestFocus();
                    } else {
                        finish = true;

                        bearing_FR_input.setError(null);
                        bearing_FA_input.setError(null);
                        bearing_i_input.setError(null);
                        bearing_Cr_input.setError(null);
                        bearing_C0r_input.setError(null);
                        bearing_X_input.setError(null);
                        bearing_Y_input.setError(null);
                        bearing_e_input.setError(null);
                        bearing_fp_input.setError(null);
                        bearing_ft_input.setError(null);
                        bearing_n_input.setError(null);

                        if (type == 1) {
                            Toast.makeText(getActivity(), "你要选一种轴承类型(¯―¯٥)", Toast.LENGTH_SHORT).show();
                            focus = bearing_style;
                            focus.requestFocus();
                        } else {
                            double ft = Double.valueOf(bearing_ft.getText().toString());
                            double n = Double.valueOf(bearing_n.getText().toString());

                            String L = getResources().getString(R.string.bearing_L) + ":\t" + getL(Cr, ft, P, type);
                            String Lh = getResources().getString(R.string.bearing_Lh) + ":\t" + getLh(n, Cr, ft, P, type);

                            ((TextView) view.findViewById(R.id.bearing_L)).setText(L);
                            ((TextView) view.findViewById(R.id.bearing_Lh)).setText(Lh);

                            flag = true;
                        }
                    }
                }
                return false;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear = true;

                bearing_FR.getText().clear();
                bearing_FA.getText().clear();
                bearing_i.getText().clear();
                bearing_Cr.getText().clear();
                bearing_C0r.getText().clear();
                bearing_X.getText().clear();
                bearing_Y.getText().clear();
                bearing_e.getText().clear();
                bearing_fp.getText().clear();
                bearing_ft.getText().clear();
                bearing_n.getText().clear();

                flag = flag_iFAC0r = flag_FAFR = finish = false;

                if (type != 1) {
                    Log.e("clear1", clear + "");
                    bearing_style.clearCheck();
                }
                type = 1;

                getFragmentManager()
                        .beginTransaction()
                        .detach(DesignBearingFragment.this)
                        .attach(DesignBearingFragment.this)
                        .commit();

                bearing_FR_input.setError(null);
                bearing_FA_input.setError(null);
                bearing_i_input.setError(null);
                bearing_Cr_input.setError(null);
                bearing_C0r_input.setError(null);
                bearing_X_input.setError(null);
                bearing_Y_input.setError(null);
                bearing_e_input.setError(null);
                bearing_fp_input.setError(null);
                bearing_ft_input.setError(null);
                bearing_n_input.setError(null);

                bearing_e_input.setHelperText(null);
                bearing_X_input.setHelperText(null);
                bearing_Y_input.setHelperText(null);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", getS(L, Lh));
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), R.string.copy_done, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.copy_false, Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private double getFAFR(double FA, double FR) {
        flag_FAFR = true;
        FAFR = FA / FR;
        return FAFR;
    }

    private double getIFAC0r(int i, double FA, double C0r) {
        flag_iFAC0r = true;
        iFAC0r = (i * FA) / (C0r * 1000);
        return iFAC0r;
    }

    private double getP(double X, double FR, double Y, double FA) {
        P = X * FR + Y * FA;
        return P;
    }

    private double getL(double Cr, double ft, double P, double epi) {
        L = 1000000 * Math.pow(Cr * ft / P, epi);
        return L;
    }

    private double getLh(double n, double Cr, double ft, double P, double epi) {
        Lh = 1000000 / (60 * n) * Math.pow(Cr * ft / P, epi);
        return Lh;
    }

    private String getS(double L, double Lh) {
        return "寿命L(次数)\t=\t" + L + "\n" +
                "寿命Lh(小时)\t=\t" + Lh;
    }
}
