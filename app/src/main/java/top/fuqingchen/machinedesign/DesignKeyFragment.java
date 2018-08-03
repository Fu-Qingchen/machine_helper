package top.fuqingchen.machinedesign;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 */
public class DesignKeyFragment extends Fragment {
    View view;
    boolean flag = false, flag_d = false, clear = false;
    FlagKey flagKey = new FlagKey();
    TextInputLayout key_input_d, key_input_l;
    AutoCompleteTextView key_d, key_l;
    RadioGroup key_style;
    RadioButton key_soft, key_normal, key_hard;
    double temD;
    int type = 4;


    public DesignKeyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_design_key, container, false);

        key_d = view.findViewById(R.id.key_d);
        key_l = view.findViewById(R.id.key_l);

        key_input_d = view.findViewById(R.id.key_input_d);
        key_input_l = view.findViewById(R.id.key_input_l);

        key_style = view.findViewById(R.id.key_style);
        key_soft = view.findViewById(R.id.key_soft);
        key_normal = view.findViewById(R.id.key_normal);
        key_hard = view.findViewById(R.id.key_hard);

        TextView delete = view.findViewById(R.id.key_delete);
        TextView copy = view.findViewById(R.id.key_copy);

        key_d.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!TextUtils.isEmpty(key_d.getText().toString())) {
                    temD = Double.valueOf(key_d.getText().toString());
                    if (temD >= 6 && temD <= 500) {
                        flagKey.setD(temD);

                        String tem_bh = getString(R.string.key_bh) + ":\t" + flagKey.getB() + "×" + flagKey.getH();
                        String tem_t = getString(R.string.key_t) + ":\t" + flagKey.getT1();
                        String tem_t_top = getString(R.string.design_top) + ":\t" + flagKey.getT1_up();
                        String tem_t_low = getString(R.string.design_button) + ":\t" + flagKey.getT1_low();
                        String tem_t1 = getString(R.string.key_t1) + ":\t" + flagKey.getT2();
                        String tem_t1_top = getString(R.string.design_top) + ":\t" + flagKey.getT2_up();
                        String tem_t1_low = getString(R.string.design_button) + ":\t" + flagKey.getT2_low();
                        String tem_r_min = getString(R.string.key_r_min) + ":\t" + flagKey.getR_min();
                        String tem_r_max = getString(R.string.key_r_max) + ":\t" + flagKey.getR_max();
                        String tem_b = getString(R.string.key_b) + ":\t" + flagKey.getB();

                        flag = true;
                        key_input_d.setError(null);

                        ((TextView) view.findViewById(R.id.key_bh)).setText(tem_bh);
                        ((TextView) view.findViewById(R.id.key_t)).setText(tem_t);
                        ((TextView) view.findViewById(R.id.key_t_top)).setText(tem_t_top);
                        ((TextView) view.findViewById(R.id.key_t_button)).setText(tem_t_low);
                        ((TextView) view.findViewById(R.id.key_t1)).setText(tem_t1);
                        ((TextView) view.findViewById(R.id.key_t1_top)).setText(tem_t1_top);
                        ((TextView) view.findViewById(R.id.key_t1_button)).setText(tem_t1_low);
                        ((TextView) view.findViewById(R.id.key_r_min)).setText(tem_r_min);
                        ((TextView) view.findViewById(R.id.key_r_max)).setText(tem_r_max);
                        ((TextView) view.findViewById(R.id.key_b)).setText(tem_b);


                        if (type != 4) {
                            flagKey.setType(temD, type);
                            String tem_key_1 = getString(R.string.key_1) + ":\t" + flagKey.getType1();
                            String tem_key_2 = getString(R.string.key_2) + ":\t" + flagKey.getType2();
                            String tem_b1_top = getString(R.string.design_top) + ":\t" + flagKey.getB1_up();
                            String tem_b1_low = getString(R.string.design_button) + ":\t" + flagKey.getB1_low();
                            String tem_b2_top = getString(R.string.design_top) + ":\t" + flagKey.getB2_up();
                            String tem_b2_low = getString(R.string.design_button) + ":\t" + flagKey.getB2_low();

                            ((TextView) view.findViewById(R.id.key_b1_type)).setText(tem_key_1);
                            ((TextView) view.findViewById(R.id.key_b2_type)).setText(tem_key_2);
                            ((TextView) view.findViewById(R.id.key_b1_top)).setText(tem_b1_top);
                            ((TextView) view.findViewById(R.id.key_b1_button)).setText(tem_b1_low);
                            ((TextView) view.findViewById(R.id.key_b2_top)).setText(tem_b2_top);
                            ((TextView) view.findViewById(R.id.key_b2_button)).setText(tem_b2_low);
                        }

                        flag_d = true;

                    } else {
                        flag_d = false;
                        key_input_d.setError(getString(R.string.key_d_info));
                    }

                } else {
                    flag_d = false;
                    key_input_d.setError(getString(R.string.app_unWrite));
                }
                return false;
            }
        });

        key_style.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!clear) {
                    if (flag_d) {
                        RadioButton radioButton = view.findViewById(checkedId);
                        switch (radioButton.getText().toString()) {
                            case "较紧":
                                type = 2;
                                break;
                            case "较松":
                                type = 0;
                                break;
                            case "一般":
                                type = 1;
                                break;
                            default:
                                type = 4;
                        }
                        if (type != 4) {
                            flagKey.setType(temD, type);

                            String tem_key_1 = getString(R.string.key_1) + ":\t" + flagKey.getType1();
                            String tem_key_2 = getString(R.string.key_2) + ":\t" + flagKey.getType2();
                            String tem_b1_top = getString(R.string.design_top) + ":\t" + flagKey.getB1_up();
                            String tem_b1_low = getString(R.string.design_button) + ":\t" + flagKey.getB1_low();
                            String tem_b2_top = getString(R.string.design_top) + ":\t" + flagKey.getB2_up();
                            String tem_b2_low = getString(R.string.design_button) + ":\t" + flagKey.getB2_low();

                            ((TextView) view.findViewById(R.id.key_b1_type)).setText(tem_key_1);
                            ((TextView) view.findViewById(R.id.key_b2_type)).setText(tem_key_2);
                            ((TextView) view.findViewById(R.id.key_b1_top)).setText(tem_b1_top);
                            ((TextView) view.findViewById(R.id.key_b1_button)).setText(tem_b1_low);
                            ((TextView) view.findViewById(R.id.key_b2_top)).setText(tem_b2_top);
                            ((TextView) view.findViewById(R.id.key_b2_button)).setText(tem_b2_low);
                        }
                    } else {
                        key_input_d.setError(getString(R.string.app_unWrite));
                    }
                } else {
                    clear = false;
                }
            }
        });

        key_l.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!TextUtils.isEmpty(key_l.getText().toString())) {
                    int temL = Integer.valueOf(key_l.getText().toString());
                    if (temL >= 6 && temL <= 550) {
                        flagKey.setL(temL);
                        String tem_L = getString(R.string.key_L) + ":\t" + flagKey.getL();

                        flag = true;
                        key_input_l.setError(null);

                        ((TextView) view.findViewById(R.id.key_L)).setText(tem_L);
                    } else {
                        key_input_l.setError(getString(R.string.key_l_info));
                    }

                } else {
                    key_input_l.setError(getString(R.string.app_unWrite));
                }
                return false;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key_d.getText().clear();
                key_l.getText().clear();
                flag = false;
                flagKey = new FlagKey();
                clear = true;
                flag_d = false;
                if (type != 4) {
                    key_style.clearCheck();
                }
                type = 4;

                getFragmentManager()
                        .beginTransaction()
                        .detach(DesignKeyFragment.this)
                        .attach(DesignKeyFragment.this)
                        .commit();
                key_input_d.setError(null);
                key_input_l.setError(null);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    ClipboardManager clipboardManager =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Result", flagKey.toString());
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
