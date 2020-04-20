
	public class DeleteContactCommand extends Command{

	    private ContactList contactList;
	    private Contact contact;
	    private Context context;

	    public DeleteContactCommand(ContactList contactList, Contact contact, Context context) {
	        this.contactList = contactList;
	        this.contact = contact;
	        this.context = context;
	    }


	    public void execute() {
	        contactList.deleteContact(contact);
	        setIsExecuted(contactList.deleteContact(context));
	    }
	}
}
