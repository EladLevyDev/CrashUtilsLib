package com.crashreportutils.android;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by elad on 2/18/15.
 */

public class UserPickDialog extends DialogFragment {


    private static final String NEW_USER_TEXT = "Not on the list?";
    private ArrayList<String> userArrayList;
    private String error;

    // Constr
    public static UserPickDialog newInstance(String error) {
        UserPickDialog rateDialog = new UserPickDialog();
        Bundle bundle = new Bundle();
        bundle.putString("error", error);
        rateDialog.setArguments(bundle);
        return rateDialog;
    }

    // OnCreate - set style
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.UserPickDialog);
        this.error = getArguments().getString("error");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get window params
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP);

        WindowManager.LayoutParams p = window.getAttributes();
        // Show Dialog above status bar
        //Use this to show above status bar p.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;

        // init window attributes
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.MATCH_PARENT;
        p.gravity = Gravity.TOP;
        p.windowAnimations = R.style.UserPickAnimation;

        // invalidate new attributes
        window.setAttributes(p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Set dialog cancelable
        setCancelable(true);
        getDialog().setCanceledOnTouchOutside(false);

        //init Views
        View view = inflater.inflate(R.layout.dialog_crash_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        initUsersData();
        userArrayList.add(NEW_USER_TEXT);
        // fill in the grid_item layout
        UserAdapter userAdapter = new UserAdapter(userArrayList, getActivity());
        listView.setAdapter(userAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = userArrayList.get(position);
                if (!name.equalsIgnoreCase(NEW_USER_TEXT)){
                    CrashUtils.sendLogFile(error, name + CrashUtils.DOMAIN, getActivity());
                    Pref.setPriority(getActivity(), name, 1);
                    dismiss();
                } else {
                    showCustomNameDialog();
                }
            }
        });

        view.findViewById(R.id.imageViewClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private void showCustomNameDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        final EditText edittext= new EditText(getActivity());
        alert.setMessage("Enter Your Name");
        alert.setTitle("Add New User");

        alert.setView(edittext);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String value= edittext.getText().toString();
                if(value != null && value.length() > 0) {
                    Pref.addNewUser(getActivity(), value);
                    CrashUtils.sendLogFile(error, value + CrashUtils.DOMAIN, getActivity());
                    dismiss();
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();
    }


    private void initUsersData() {
        userArrayList = CrashUtils.getUserList(getActivity());
    }


}