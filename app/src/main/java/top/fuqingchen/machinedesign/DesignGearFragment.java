package top.fuqingchen.machinedesign;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;


/**
 * @author Fu_Qingchen
 */
public class DesignGearFragment extends Fragment {
    View view;
    private TextInputEditText gear_sigmaHP, gear_T1, gear_T2, gear_psi_d,
            gear_i, gear_beta0, gear_KA, gear_Kv, gear_K_beta, gear_K_alpha, gear_z1, gear_z2,
            gear_b1, gear_b2, gear_a, gear_YFS1, gear_YFS2;
    private TextInputLayout gear_sigmaHP_input,
            gear_T1_input, gear_T2_input, gear_psi_d_input, gear_i_input, gear_beta0_input,
            gear_KA_input, gear_Kv_input, gear_K_beta_input, gear_K_alpha_input, gear_z1_input,
            gear_z2_input, gear_b1_input, gear_b2_input, gear_a_input, gear_YFS1_input,
            gear_YFS2_input;
    private View focus;
    private boolean flag = false, clear = false;
    private double K, D = 0, sigma_HP, T1, iii = 0, T2, beta0, psi_d,
            mn0 = 0, mn = 0, a = 0, a0 = 0, d1 = 0, d2 = 0, da1 = 0, da2 = 0, df1 = 0, df2 = 0,
            beta = 0, sigma_YF1, sigma_YF2, sigma_F1, sigma_F2, cosBeta;
    private int z1 = 0, z2 = 0, b2 = 0, b1 = 0;
    private final static double[]
            M = {1, 1.25, 1.5, 2, 2.5, 3, 4, 5, 6, 8, 10, 12, 16, 20, 25, 32, 40, 50};

    public DesignGearFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_design_gear, container, false);

        gear_sigmaHP = view.findViewById(R.id.gear_sigmaHP);
        gear_T1 = view.findViewById(R.id.gear_T1);
        gear_T2 = view.findViewById(R.id.gear_T2);
        gear_psi_d = view.findViewById(R.id.gear_psi_d);
        gear_i = view.findViewById(R.id.gear_i);
        gear_beta0 = view.findViewById(R.id.gear_beta0);
        gear_KA = view.findViewById(R.id.gear_KA);
        gear_Kv = view.findViewById(R.id.gear_Kv);
        gear_K_beta = view.findViewById(R.id.gear_K_beta);
        gear_K_alpha = view.findViewById(R.id.gear_K_alpha);
        gear_z1 = view.findViewById(R.id.gear_z1);
        gear_z2 = view.findViewById(R.id.gear_z2);
        gear_b1 = view.findViewById(R.id.gear_b1);
        gear_b2 = view.findViewById(R.id.gear_b2);
        gear_a = view.findViewById(R.id.gear_a);
        gear_YFS1 = view.findViewById(R.id.gear_YFS1);
        gear_YFS2 = view.findViewById(R.id.gear_YFS2);

        gear_sigmaHP_input = view.findViewById(R.id.gear_sigmaHP_input);
        gear_T1_input = view.findViewById(R.id.gear_T1_input);
        gear_T2_input = view.findViewById(R.id.gear_T2_input);
        gear_psi_d_input = view.findViewById(R.id.gear_psi_d_input);
        gear_i_input = view.findViewById(R.id.gear_i_input);
        gear_beta0_input = view.findViewById(R.id.gear_beta0_input);
        gear_KA_input = view.findViewById(R.id.gear_KA_input);
        gear_Kv_input = view.findViewById(R.id.gear_Kv_input);
        gear_K_beta_input = view.findViewById(R.id.gear_K_beta_input);
        gear_K_alpha_input = view.findViewById(R.id.gear_K_alpha_input);
        gear_z1_input = view.findViewById(R.id.gear_z1_input);
        gear_z2_input = view.findViewById(R.id.gear_z2_input);
        gear_b1_input = view.findViewById(R.id.gear_b1_input);
        gear_b2_input = view.findViewById(R.id.gear_b2_input);
        gear_a_input = view.findViewById(R.id.gear_a_input);
        gear_YFS1_input = view.findViewById(R.id.gear_YFS1_input);
        gear_YFS2_input = view.findViewById(R.id.gear_YFS2_input);

        TextView copy = view.findViewById(R.id.gear_copy);
        TextView delete = view.findViewById(R.id.gear_delete);

        gear_sigmaHP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                focus = gear_T1;
                focus.requestFocus();

                return false;
            }
        });

        gear_T1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                focus = gear_T2;
                focus.requestFocus();

                return false;
            }
        });

        gear_T2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                focus = gear_psi_d;
                focus.requestFocus();

                return false;
            }
        });

        gear_psi_d.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!TextUtils.isEmpty(gear_psi_d.getText().toString())) {
                    psi_d = Double.valueOf(gear_psi_d.getText().toString());
                    printB();
                    focus = gear_i;
                    focus.requestFocus();
                }
                return false;
            }
        });

        gear_i.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (!TextUtils.isEmpty(gear_i.getText().toString())) {
                    iii = Double.valueOf(gear_i.getText().toString());
                    printZ2();
                }
                focus = gear_beta0;
                focus.requestFocus();

                return false;
            }
        });

        gear_beta0.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (!TextUtils.isEmpty(gear_beta0.getText().toString())) {
                    if (Double.valueOf(gear_beta0.getText().toString()) > 15 ||
                            Double.valueOf(gear_beta0.getText().toString()) < 8) {
                        gear_beta0_input.setError("斜齿轮常用螺旋角为8~15°");
                    } else {
                        gear_beta0_input.setError(null);
                        focus = gear_KA;
                        focus.requestFocus();
                    }
                }

                return false;
            }
        });

        gear_KA.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                focus = gear_Kv;
                focus.requestFocus();

                return false;
            }
        });

        gear_Kv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                focus = gear_K_beta;
                focus.requestFocus();

                return false;
            }
        });

        gear_K_beta.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                focus = gear_K_alpha;
                focus.requestFocus();

                return false;
            }
        });

        gear_K_alpha.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                clear = false;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(gear_K_alpha.getText().toString())) {
                        gear_K_alpha_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_K_alpha;
                    }
                    if (TextUtils.isEmpty(gear_K_beta.getText().toString())) {
                        gear_K_beta_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_K_beta;
                    }
                    if (TextUtils.isEmpty(gear_Kv.getText().toString())) {
                        gear_Kv_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_Kv;
                    }
                    if (TextUtils.isEmpty(gear_KA.getText().toString())) {
                        gear_KA_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_KA;
                    }
//                    if (TextUtils.isEmpty(gear_beta0.getText().toString())) {
//                        gear_beta0_input.setError(getString(R.string.app_unWrite));
//                        cancel = true;
//                        focus = gear_beta0;
//                    }
                    if (TextUtils.isEmpty(gear_i.getText().toString())) {
                        gear_i_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_i;
                    }
                    if (TextUtils.isEmpty(gear_psi_d.getText().toString())) {
                        gear_psi_d_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_psi_d;
                    }
//                    if (TextUtils.isEmpty(gear_T2.getText().toString())) {
//                        gear_T2_input.setError(getString(R.string.app_unWrite));
//                        cancel = true;
//                        focus = gear_T2;
//                    }
                    if (TextUtils.isEmpty(gear_T1.getText().toString())) {
                        gear_T1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_T1;
                    }

                    if (TextUtils.isEmpty(gear_sigmaHP.getText().toString())) {
                        gear_sigmaHP_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_sigmaHP;
                    }
                    if (cancel) {
                        focus.requestFocus();
                    } else {

//                            T2 = Double.valueOf(gear_T2.getText().toString());
//                            beta0 = Double.valueOf(gear_beta0.getText().toString());
                        sigma_HP = Double.valueOf(gear_sigmaHP.getText().toString());
                        T1 = Double.valueOf(gear_T1.getText().toString());
                        iii = Double.valueOf(gear_i.getText().toString());
                        double psi_d = Double.valueOf(gear_psi_d.getText().toString());
                        double KA = Double.valueOf(gear_KA.getText().toString());
                        double Kv = Double.valueOf(gear_Kv.getText().toString());
                        double K_beta = Double.valueOf(gear_K_beta.getText().toString());
                        double K_alpha = Double.valueOf(gear_K_alpha.getText().toString());

//                        String tem_D = getString(R.id.gear_d10) + ":\t" +
//                                getD(getK(KA, Kv, K_beta, K_alpha), T1, psi_d, sigma_HP, iii);
//
//                        ((TextView) view.findViewById(R.id.gear_d10)).setText(tem_D);

                        gear_T2_input.setError(null);
                        gear_beta0_input.setError(null);
                        gear_sigmaHP_input.setError(null);
                        gear_T1_input.setError(null);
                        gear_psi_d_input.setError(null);
                        gear_i_input.setError(null);
                        gear_KA_input.setError(null);
                        gear_Kv_input.setError(null);
                        gear_K_beta_input.setError(null);
                        gear_K_alpha_input.setError(null);


                        String tem_D = getString(R.string.gear_d10) + ":\t" +
                                getD(getK(KA, Kv, K_beta, K_alpha), T1, psi_d, sigma_HP, iii);
                        ((TextView) view.findViewById(R.id.gear_d10)).setText(tem_D);

                        focus = gear_z1;
                        focus.requestFocus();
                    }

                }
                return false;
            }
        });

        gear_z1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (!TextUtils.isEmpty(gear_z1.getText().toString())) {
                    z1 = Integer.valueOf(gear_z1.getText().toString());
                    printZ2();
                }
                focus = gear_z2;
                focus.requestFocus();

                return false;
            }
        });


        gear_z2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                clear = false;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(gear_z2.getText().toString())) {
                        gear_z2_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_z2;
                    }
                    if (TextUtils.isEmpty(gear_z1.getText().toString())) {
                        gear_z1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_z1;
                    }
                    if (TextUtils.isEmpty(gear_K_alpha.getText().toString())) {
                        gear_K_alpha_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_K_alpha;
                    }
                    if (TextUtils.isEmpty(gear_K_beta.getText().toString())) {
                        gear_K_beta_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_K_beta;
                    }
                    if (TextUtils.isEmpty(gear_Kv.getText().toString())) {
                        gear_Kv_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_Kv;
                    }
                    if (TextUtils.isEmpty(gear_KA.getText().toString())) {
                        gear_KA_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_KA;
                    }
                    if (TextUtils.isEmpty(gear_beta0.getText().toString())) {
                        gear_beta0_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_beta0;
                    }
                    if (TextUtils.isEmpty(gear_i.getText().toString())) {
                        gear_i_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_i;
                    }
                    if (TextUtils.isEmpty(gear_psi_d.getText().toString())) {
                        gear_psi_d_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_psi_d;
                    }
//                    if (TextUtils.isEmpty(gear_T2.getText().toString())) {
//                        gear_T2_input.setError(getString(R.string.app_unWrite));
//                        cancel = true;
//                        focus = gear_T2;
//                    }
                    if (TextUtils.isEmpty(gear_T1.getText().toString())) {
                        gear_T1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_T1;
                    }

                    if (TextUtils.isEmpty(gear_sigmaHP.getText().toString())) {
                        gear_sigmaHP_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_sigmaHP;
                    }

                    if (cancel) {
                        focus.requestFocus();
                    } else {

//                            T2 = Double.valueOf(gear_T2.getText().toString());
                        beta0 = Double.valueOf(gear_beta0.getText().toString());
                        sigma_HP = Double.valueOf(gear_sigmaHP.getText().toString());
                        T1 = Double.valueOf(gear_T1.getText().toString());
                        iii = Double.valueOf(gear_i.getText().toString());
                        z1 = Integer.valueOf(gear_z1.getText().toString());
                        z2 = Integer.valueOf(gear_z2.getText().toString());
                        double psi_d = Double.valueOf(gear_psi_d.getText().toString());
                        double KA = Double.valueOf(gear_KA.getText().toString());
                        double Kv = Double.valueOf(gear_Kv.getText().toString());
                        double K_beta = Double.valueOf(gear_K_beta.getText().toString());
                        double K_alpha = Double.valueOf(gear_K_alpha.getText().toString());

                        String tem_D = getString(R.id.gear_d10) + ":\t" +
                                getD(getK(KA, Kv, K_beta, K_alpha), T1, psi_d, sigma_HP, iii);

                        ((TextView) view.findViewById(R.id.gear_d10)).setText(tem_D);

                        getMn0();
                        String temMn = "法向模数mn:\t" + mn + "\t(" + mn0 + ")";
                        ((TextView) view.findViewById(R.id.gear_mn)).setText(temMn);
                        gear_a_input.setHelperText("计算值:a=\t" + a0);

                        gear_T2_input.setError(null);
                        gear_beta0_input.setError(null);
                        gear_sigmaHP_input.setError(null);
                        gear_T1_input.setError(null);
                        gear_psi_d_input.setError(null);
                        gear_i_input.setError(null);
                        gear_KA_input.setError(null);
                        gear_Kv_input.setError(null);
                        gear_K_beta_input.setError(null);
                        gear_K_alpha_input.setError(null);
                        gear_z1_input.setError(null);
                        gear_z2_input.setError(null);

                        focus = gear_a;
                        focus.requestFocus();
                    }

                }
                return false;
            }
        });

        gear_a.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                clear = false;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(gear_a.getText().toString())) {
                        gear_a_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_a;
                    }
                    if (TextUtils.isEmpty(gear_z2.getText().toString())) {
                        gear_z2_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_z2;
                    }
                    if (TextUtils.isEmpty(gear_z1.getText().toString())) {
                        gear_z1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_z1;
                    }
                    if (TextUtils.isEmpty(gear_K_alpha.getText().toString())) {
                        gear_K_alpha_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_K_alpha;
                    }
                    if (TextUtils.isEmpty(gear_K_beta.getText().toString())) {
                        gear_K_beta_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_K_beta;
                    }
                    if (TextUtils.isEmpty(gear_Kv.getText().toString())) {
                        gear_Kv_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_Kv;
                    }
                    if (TextUtils.isEmpty(gear_KA.getText().toString())) {
                        gear_KA_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_KA;
                    }
                    if (TextUtils.isEmpty(gear_beta0.getText().toString())) {
                        gear_beta0_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_beta0;
                    }
                    if (TextUtils.isEmpty(gear_i.getText().toString())) {
                        gear_i_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_i;
                    }
                    if (TextUtils.isEmpty(gear_psi_d.getText().toString())) {
                        gear_psi_d_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_psi_d;
                    }
//                    if (TextUtils.isEmpty(gear_T2.getText().toString())) {
//                        gear_T2_input.setError(getString(R.string.app_unWrite));
//                        cancel = true;
//                        focus = gear_T2;
//                    }
                    if (TextUtils.isEmpty(gear_T1.getText().toString())) {
                        gear_T1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_T1;
                    }

                    if (TextUtils.isEmpty(gear_sigmaHP.getText().toString())) {
                        gear_sigmaHP_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_sigmaHP;
                    }
                    if (cancel) {
                        focus.requestFocus();
                    } else {

//                            T2 = Double.valueOf(gear_T2.getText().toString());
                        beta0 = Double.valueOf(gear_beta0.getText().toString());
                        sigma_HP = Double.valueOf(gear_sigmaHP.getText().toString());
                        T1 = Double.valueOf(gear_T1.getText().toString());
                        iii = Double.valueOf(gear_i.getText().toString());
                        z1 = Integer.valueOf(gear_z1.getText().toString());
                        z2 = Integer.valueOf(gear_z2.getText().toString());
                        a = Double.valueOf(gear_a.getText().toString());
                        psi_d = Double.valueOf(gear_psi_d.getText().toString());
                        double KA = Double.valueOf(gear_KA.getText().toString());
                        double Kv = Double.valueOf(gear_Kv.getText().toString());
                        double K_beta = Double.valueOf(gear_K_beta.getText().toString());
                        double K_alpha = Double.valueOf(gear_K_alpha.getText().toString());

                        String tem_D = getString(R.id.gear_d10) + ":\t" +
                                getD(getK(KA, Kv, K_beta, K_alpha), T1, psi_d, sigma_HP, iii);

                        ((TextView) view.findViewById(R.id.gear_d10)).setText(tem_D);

                        getMn0();
                        String temMn = "法向模数mn:\t" + mn + "\t(" + mn0 + ")";
                        ((TextView) view.findViewById(R.id.gear_mn)).setText(temMn);
                        gear_a_input.setHelperText("计算值:a=\t" + a0);

                        getbeta();
                        String temBeta = getString(R.string.gear_beta) + ":\t" + beta;
                        ((TextView) view.findViewById(R.id.gear_beta)).setText(temBeta);
                        String temD1 = getString(R.string.gear_d1) + ":\t" + d1;
                        ((TextView) view.findViewById(R.id.gear_d1)).setText(temD1);
                        String temD2 = getString(R.string.gear_d2) + ":\t" + d2;
                        ((TextView) view.findViewById(R.id.gear_d2)).setText(temD2);
                        String temDa1 = getString(R.string.gear_da1) + ":\t" + da1;
                        ((TextView) view.findViewById(R.id.gear_da1)).setText(temDa1);
                        String temDa2 = getString(R.string.gear_da2) + ":\t" + da2;
                        ((TextView) view.findViewById(R.id.gear_da2)).setText(temDa2);
                        String temDf1 = getString(R.string.gear_df1) + ":\t" + df1;
                        ((TextView) view.findViewById(R.id.gear_df1)).setText(temDf1);
                        String temDf2 = getString(R.string.gear_df2) + ":\t" + df2;
                        ((TextView) view.findViewById(R.id.gear_df2)).setText(temDf2);

                        gear_T2_input.setError(null);
                        gear_beta0_input.setError(null);
                        gear_sigmaHP_input.setError(null);
                        gear_T1_input.setError(null);
                        gear_psi_d_input.setError(null);
                        gear_i_input.setError(null);
                        gear_KA_input.setError(null);
                        gear_Kv_input.setError(null);
                        gear_K_beta_input.setError(null);
                        gear_K_alpha_input.setError(null);
                        gear_z1_input.setError(null);
                        gear_z2_input.setError(null);
                        gear_a_input.setError(null);

                        printB();

                        focus = gear_b2;
                        focus.requestFocus();
                    }
                }
                return false;
            }
        });


        gear_YFS2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                clear = false;
                boolean cancel = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(gear_YFS2.getText().toString())) {
                        gear_YFS2_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_YFS2;
                    }
                    if (TextUtils.isEmpty(gear_YFS1.getText().toString())) {
                        gear_YFS1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_YFS1;
                    }
                    if (TextUtils.isEmpty(gear_b1.getText().toString())) {
                        gear_b1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_b1;
                    }
                    if (TextUtils.isEmpty(gear_b2.getText().toString())) {
                        gear_b2_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_b2;
                    }
                    if (TextUtils.isEmpty(gear_a.getText().toString())) {
                        gear_a_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_a;
                    }
                    if (TextUtils.isEmpty(gear_z2.getText().toString())) {
                        gear_z2_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_z2;
                    }
                    if (TextUtils.isEmpty(gear_z1.getText().toString())) {
                        gear_z1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_z1;
                    }
                    if (TextUtils.isEmpty(gear_K_alpha.getText().toString())) {
                        gear_K_alpha_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_K_alpha;
                    }
                    if (TextUtils.isEmpty(gear_K_beta.getText().toString())) {
                        gear_K_beta_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_K_beta;
                    }
                    if (TextUtils.isEmpty(gear_Kv.getText().toString())) {
                        gear_Kv_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_Kv;
                    }
                    if (TextUtils.isEmpty(gear_KA.getText().toString())) {
                        gear_KA_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_KA;
                    }
                    if (TextUtils.isEmpty(gear_beta0.getText().toString())) {
                        gear_beta0_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_beta0;
                    }
                    if (TextUtils.isEmpty(gear_i.getText().toString())) {
                        gear_i_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_i;
                    }
                    if (TextUtils.isEmpty(gear_psi_d.getText().toString())) {
                        gear_psi_d_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_psi_d;
                    }
                    if (TextUtils.isEmpty(gear_T2.getText().toString())) {
                        gear_T2_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_T2;
                    }
                    if (TextUtils.isEmpty(gear_T1.getText().toString())) {
                        gear_T1_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_T1;
                    }

                    if (TextUtils.isEmpty(gear_sigmaHP.getText().toString())) {
                        gear_sigmaHP_input.setError(getString(R.string.app_unWrite));
                        cancel = true;
                        focus = gear_sigmaHP;
                    }
                    if (cancel) {
                        focus.requestFocus();
                    } else {
                        T2 = Double.valueOf(gear_T2.getText().toString());
                        beta0 = Double.valueOf(gear_beta0.getText().toString());
                        sigma_HP = Double.valueOf(gear_sigmaHP.getText().toString());
                        T1 = Double.valueOf(gear_T1.getText().toString());
                        iii = Double.valueOf(gear_i.getText().toString());
                        z1 = Integer.valueOf(gear_z1.getText().toString());
                        z2 = Integer.valueOf(gear_z2.getText().toString());
                        a = Double.valueOf(gear_a.getText().toString());
                        psi_d = Double.valueOf(gear_psi_d.getText().toString());
                        sigma_YF1 = Double.valueOf(gear_YFS1.getText().toString());
                        sigma_YF2 = Double.valueOf(gear_YFS2.getText().toString());
                        b2 = Integer.valueOf(gear_b2.getText().toString());
                        b1 = Integer.valueOf(gear_b1.getText().toString());
                        double KA = Double.valueOf(gear_KA.getText().toString());
                        double Kv = Double.valueOf(gear_Kv.getText().toString());
                        double K_beta = Double.valueOf(gear_K_beta.getText().toString());
                        double K_alpha = Double.valueOf(gear_K_alpha.getText().toString());

                        String tem_D = getString(R.id.gear_d10) + ":\t" +
                                getD(getK(KA, Kv, K_beta, K_alpha), T1, psi_d, sigma_HP, iii);

                        ((TextView) view.findViewById(R.id.gear_d10)).setText(tem_D);

                        getMn0();
                        String temMn = "法向模数mn:\t" + mn + "\t(" + mn0 + ")";
                        ((TextView) view.findViewById(R.id.gear_mn)).setText(temMn);
                        gear_a_input.setHelperText("计算值:a=\t" + a0);

                        getbeta();
                        String temBeta = getString(R.string.gear_beta) + ":\t" + beta;
                        ((TextView) view.findViewById(R.id.gear_beta)).setText(temBeta);
                        String temD1 = getString(R.string.gear_d1) + ":\t" + d1;
                        ((TextView) view.findViewById(R.id.gear_d1)).setText(temD1);
                        String temD2 = getString(R.string.gear_d2) + ":\t" + d2;
                        ((TextView) view.findViewById(R.id.gear_d2)).setText(temD2);
                        String temDa1 = getString(R.string.gear_da1) + ":\t" + da1;
                        ((TextView) view.findViewById(R.id.gear_da1)).setText(temDa1);
                        String temDa2 = getString(R.string.gear_da2) + ":\t" + da2;
                        ((TextView) view.findViewById(R.id.gear_da2)).setText(temDa2);
                        String temDf1 = getString(R.string.gear_df1) + ":\t" + df1;
                        ((TextView) view.findViewById(R.id.gear_df1)).setText(temDf1);
                        String temDf2 = getString(R.string.gear_df2) + ":\t" + df2;
                        ((TextView) view.findViewById(R.id.gear_df2)).setText(temDf2);

                        getsigma();
                        String temSigmaF1 = getString(R.string.gear_sigma_F1) + ":\t" + sigma_F1;
                        ((TextView) view.findViewById(R.id.gear_sigma_F1)).setText(temSigmaF1);
                        String temSigmaF2 = getString(R.string.gear_sigma_F2) + ":\t" + sigma_F2;
                        ((TextView) view.findViewById(R.id.gear_sigma_F2)).setText(temSigmaF2);

                        gear_T2_input.setError(null);
                        gear_beta0_input.setError(null);
                        gear_sigmaHP_input.setError(null);
                        gear_T1_input.setError(null);
                        gear_psi_d_input.setError(null);
                        gear_i_input.setError(null);
                        gear_KA_input.setError(null);
                        gear_Kv_input.setError(null);
                        gear_K_beta_input.setError(null);
                        gear_K_alpha_input.setError(null);
                        gear_z1_input.setError(null);
                        gear_z2_input.setError(null);
                        gear_a_input.setError(null);
                        gear_YFS1_input.setError(null);
                        gear_YFS2_input.setError(null);
                        gear_b1_input.setError(null);
                        gear_b2_input.setError(null);

                        printB();

                        flag = true;
                    }

                }
                return false;
            }
        });

        copy.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (flag) {
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", getS());
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(getContext(), R.string.copy_done, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.copy_false, Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear = true;

                gear_sigmaHP.getText().clear();
                gear_T1.getText().clear();
                gear_T2.getText().clear();
                gear_psi_d.getText().clear();
                gear_i.getText().clear();
                gear_beta0.getText().clear();
                gear_KA.getText().clear();
                gear_Kv.getText().clear();
                gear_K_beta.getText().clear();
                gear_K_alpha.getText().clear();
                gear_z1.getText().clear();
                gear_z2.getText().clear();
                gear_a.getText().clear();
                gear_b1.getText().clear();
                gear_b2.getText().clear();
                gear_YFS1.getText().clear();
                gear_YFS2.getText().clear();

                flag = false;

                getFragmentManager()
                        .beginTransaction()
                        .detach(DesignGearFragment.this)
                        .attach(DesignGearFragment.this)
                        .commit();

                gear_T2_input.setError(null);
                gear_beta0_input.setError(null);
                gear_sigmaHP_input.setError(null);
                gear_T1_input.setError(null);
                gear_psi_d_input.setError(null);
                gear_i_input.setError(null);
                gear_KA_input.setError(null);
                gear_Kv_input.setError(null);
                gear_K_beta_input.setError(null);
                gear_K_alpha_input.setError(null);
                gear_z1_input.setError(null);
                gear_z2_input.setError(null);
                gear_a_input.setError(null);
                gear_YFS1_input.setError(null);
                gear_YFS2_input.setError(null);
                gear_b1_input.setError(null);
                gear_b2_input.setError(null);

                gear_z2_input.setHelperText(null);
                gear_a_input.setHelperText(null);
                gear_b2_input.setHelperText(null);
            }
        });


        return view;
    }

    private double getK(double KA, double Kv, double Kbeta, double Kalpha) {
        K = KA * Kv * Kalpha * Kbeta;
        return K;
    }

    private double getD(double K, double T, double psi_d, double sigma_HP, double i) {
        D = 753 * Math.pow((K * T * (i + 1)) / (psi_d * sigma_HP * sigma_HP * i), 1.0 / 3);
        return D;
    }

    private void getMn0() {
        mn0 = D * Math.cos(beta0 / 180 * Math.PI) / z1;
        if (mn0 <= 50) {
            mn = 1;
            for (int i = 0; mn0 > M[i]; i++) {
                mn = M[i + 1];
            }
            a0 = mn * (z1 + z2) / (2 * Math.cos(beta0 / 180 * Math.PI));
        }
    }

    private void getbeta() {
        cosBeta = mn * (z1 + z2) / (2 * a);
        beta = Math.acos(cosBeta) * 180 / Math.PI;
        d1 = mn * z1 / cosBeta;
        d2 = mn * z2 / cosBeta;
        da1 = d1 + 2 * mn;
        da2 = d2 + 2 * mn;
        df1 = d1 - 2.5 * mn;
        df2 = d2 - 2.5 * mn;
    }

    private void printZ2() {
        if (!(z1 == 0 || iii == 0)) {
            double temZ2 = z1 * iii;
            gear_z2_input.setHelperText("z2≈" + temZ2);
        }
    }

    private void printB() {
        if (!(psi_d == 0 || d2 == 0)) {
            double temB2 = psi_d * d1;
            gear_b2_input.setHelperText("b2≈" + temB2);
        }
    }

    private void getsigma() {
        double zv1 = z1 / Math.pow(cosBeta, 3),
                zv2 = z2 / Math.pow(cosBeta, 3);
        double epsilon = (1.88 - 3.2 * (1.0 / zv1 + 1.0 / zv2)) * cosBeta;
        double Y_epi = 0.25 + 0.75 / epsilon, Y_beta = 1 - beta / 120;
        sigma_F1 = sigma_YF1 * Y_epi * Y_beta * 2000 * K * T1 / b2 / d1 / mn;
        sigma_F2 = sigma_YF2 * Y_epi * Y_beta * 2000 * K * T2 / b2 / d2 / mn;
    }

    private String getS() {
        return "法向模数mn\t=\t" + mn + "\n" +
                "中心距a\t=\t" + a + "\n" +
                "螺旋角β\t=\t" + beta + "\n" +
                "小齿轮分度圆d1\t=\t" + d1 + "\n" +
                "小齿轮分度圆d2\t=\t" + d2 + "\n" +
                "小齿轮分度圆da1\t=\t" + da1 + "\n" +
                "小齿轮分度圆da2\t=\t" + da2 + "\n" +
                "小齿轮分度圆df1\t=\t" + df1 + "\n" +
                "小齿轮分度圆df2\t=\t" + df2 + "\n" +
                "小齿轮齿宽b1\t=\t" + b1 + "\n" +
                "小齿轮齿宽b2\t=\t" + b2 + "\n";
    }
}