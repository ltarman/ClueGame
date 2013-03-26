package Board;

public class BadConfigFormatException extends Exception {
	public enum errorType {
		INVALID_ROOM("Room not in legend"),INVALID_ROWS("Not all rows have same number of columns"),
		INVALID_LEGEND("Row in legend has invalid format");
		
		private String output;
		errorType(String str) {this.output = str;}
		public String toString() {return output;}
	}
	
	private errorType type;
	public BadConfigFormatException(errorType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return ("Error: " + this.type);
	}
}
