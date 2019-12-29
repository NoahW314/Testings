
public class EnumInterface {
	public interface RequestAction{}
	public enum EventAction implements RequestAction {EVENT, MATCH, HELP};
	public enum TeamAction implements RequestAction  {TEAM_AT_EVENT, TEAM, TEAM_OPR, TEAM_EVENT_OPR, HELP};
	public enum SubscribeAction implements RequestAction {SUBSCRIBE, UNSUBSCRIBE, LIST_SUBS, UNSUBSCRIBE_ALL, NONE, HELP};
	
	public static void main(String[] args) {
		RequestAction a = TeamAction.HELP;
		RequestAction b = EventAction.HELP;
		
		Class<? extends RequestAction> c = a.getClass();
		if (c.equals(TeamAction.class)) {
			System.out.println("Team Actioning");
		}
	}
}
