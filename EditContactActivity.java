

	public class EditContactActivity extends AppCompatActivity {

	    private ContactList contact_list = new ContactList();
	    private Contact contact;
	    private EditText email;
	    private EditText username;
	    private Context context;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_edit_contact);

	        context = getApplicationContext();
	        contact_list.loadContacts(context);

	        Intent intent = getIntent();
	        int pos = intent.getIntExtra("position", 0);

	        contact = contact_list.getContact(pos);

	        username = (EditText) findViewById(R.id.username);
	        email = (EditText) findViewById(R.id.email);

	        username.setText(contact.getUsername());
	        email.setText(contact.getEmail());
	    }

	    public void saveContact(View view) {

	        String email_str = email.getText().toString();

	        if (email_str.equals("")) {
	            email.setError("Empty field!");
	            return;
	        }

	        if (!email_str.contains("@")){
	            email.setError("Must be an email address!");
	            return;
	        }

	        String username_str = username.getText().toString();
	        String id = contact.getId(); // Reuse the contact id

	        // Check that username is unique AND username is changed (Note: if username was not changed
	        // then this should be fine, because it was already unique.)
	        if (!contact_list.isUsernameAvailable(username_str) && !(contact.getUsername().equals(username_str))) {
	            username.setError("Username already taken!");
	            return;
	        }

	        Contact updated_contact = new Contact(username_str, email_str, id);

	        contact_list.deleteContact(contact);
	        contact_list.addContact(updated_contact);
	        contact_list.saveContacts(context);

	        EditContactCommand editContactCommand = new EditContactCommand(contact_list, contact, updated_contact, context);
	        editContactCommand.execute();

	        boolean success = editContactCommand.isExecuted();
	        if(!success) {
	            return;
	        }

	        // End EditContactActivity
	        finish();
	    }

	    public void deleteContact(View view) {

	        contact_list.deleteContact(contact);
	        contact_list.saveContacts(context);

	        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(contact_list, contact, context);
	        deleteContactCommand.execute();

	        boolean success = deleteContactCommand.isExecuted();
	        if(!success) {
	            return;
	        }

	        // End EditContactActivity
	        finish();
	    }
	}




