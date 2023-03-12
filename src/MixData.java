
public class MixData {
	public int id; // id
	public String key; // key
	public String value; // value
	
	public Object[] true_value;
	public Object[] false_value;
	public Object[] data;
	
	public int type;
	public int parent = 0;
	
	
	public MixData( String a, String b, int c, int i) {
		id = i; // id
		key = a; // key
		value = b; // value
		type = c; // type
	}
	
	
	public MixData(String a, String b, int c, int i, int p) {
		id = i;
		key = a;
		value = b;
		type = c;
		parent = p;
	}
	
	
	public MixData(String a, String b, Object[] items_true, Object[] items_false, int c, int i,int p) {
		key = a;
		value = b;
		true_value = items_true;
		false_value = items_false;
		type = c;
		id = i;
		parent = p;
	}
	
	public MixData(String a, String b, Object[] mix_data, int c, int i,int p) {
		key = a;
		value = b;
		data = mix_data;
		type = c;
		id = i;
		parent = p;
	}
	
	public String toString() {
      return key;
   }

}
