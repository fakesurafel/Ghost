package org.telegram.ui;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.messenger.SessionExportHelper;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.LayoutHelper;

/**
 * Ghost Gram login-method chooser.
 *
 * The existing phone login remains the authoritative account activation flow.
 * Session and API credential forms validate input and hand off only when the
 * underlying client can safely authenticate; they never create fake auth keys.
 */
public class GhostGramLoginActivity extends BaseFragment {
    private LinearLayout content;

    @Override
    public View createView(Context context) {
        actionBar.setTitle(getString(R.string.LoginMethod));

        content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.TOP);
        content.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(24), AndroidUtilities.dp(24), AndroidUtilities.dp(24));

        TextView title = new TextView(context);
        title.setText(getString(R.string.AppName));
        title.setTextSize(28);
        title.setGravity(Gravity.CENTER);
        content.addView(title, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 18));

        TextView description = new TextView(context);
        description.setText(getString(R.string.SessionImportNotAvailable));
        description.setTextSize(14);
        description.setGravity(Gravity.CENTER);
        content.addView(description, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 24));

        Button phone = button(context, getString(R.string.LoginMethodNormal));
        phone.setOnClickListener(v -> presentFragment(new LoginActivity(), true));
        content.addView(phone, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 48, 0, 0, 0, 12));

        Button session = button(context, getString(R.string.LoginMethodSession));
        session.setOnClickListener(v -> showSessionForm(context));
        content.addView(session, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 48, 0, 0, 0, 12));

        Button api = button(context, getString(R.string.LoginMethodApi));
        api.setOnClickListener(v -> showApiForm(context));
        content.addView(api, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 48));

        fragmentView = content;
        return content;
    }

    private Button button(Context context, String text) {
        Button button = new Button(context);
        button.setText(text);
        button.setAllCaps(false);
        return button;
    }

    private void showSessionForm(Context context) {
        content.removeAllViews();
        addHeading(context, getString(R.string.LoginMethodSession), getString(R.string.SessionImportNotAvailable));

        EditText session = new EditText(context);
        session.setHint(getString(R.string.SessionString));
        session.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        content.addView(session, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 56, 0, 0, 0, 16));

        Button validate = button(context, getString(R.string.LoginMethodSession));
        validate.setOnClickListener(v -> {
            String value = session.getText().toString().trim();
            if (TextUtils.isEmpty(value) || !SessionExportHelper.isSupportedSessionString(value)) {
                Toast.makeText(context, getString(R.string.SessionStringInvalid), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(context, getString(R.string.SessionImportNotAvailable), Toast.LENGTH_LONG).show();
        });
        content.addView(validate, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 48));
    }

    private void showApiForm(Context context) {
        content.removeAllViews();
        addHeading(context, getString(R.string.LoginMethodApi), getString(R.string.StartText));

        EditText apiId = new EditText(context);
        apiId.setHint(getString(R.string.ApiId));
        apiId.setInputType(InputType.TYPE_CLASS_NUMBER);
        content.addView(apiId, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 56, 0, 0, 0, 10));

        EditText apiHash = new EditText(context);
        apiHash.setHint(getString(R.string.ApiHash));
        apiHash.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        content.addView(apiHash, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 56, 0, 0, 0, 16));

        Button continueButton = button(context, getString(R.string.LoginMethodNormal));
        continueButton.setOnClickListener(v -> {
            String id = apiId.getText().toString().trim();
            String hash = apiHash.getText().toString().trim();
            if (TextUtils.isEmpty(id) || TextUtils.isEmpty(hash)) {
                Toast.makeText(context, getString(R.string.SessionStringInvalid), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(context, getString(R.string.StartText), Toast.LENGTH_LONG).show();
            presentFragment(new LoginActivity(), true);
        });
        content.addView(continueButton, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 48));
    }

    private void addHeading(Context context, String titleText, String descriptionText) {
        TextView title = new TextView(context);
        title.setText(titleText);
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        content.addView(title, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 12));

        TextView description = new TextView(context);
        description.setText(descriptionText);
        description.setTextSize(14);
        description.setGravity(Gravity.CENTER);
        content.addView(description, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 20));
    }
}
