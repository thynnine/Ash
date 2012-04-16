public class StringVariable{

    private String contents;
    private String name;

    public StringVariable(String name, String value){
	this.name = name;
	this.contents = value;
    }

    public StringVariable copy(){
	StringVariable copy = new StringVariable(name,contents);
	return copy;
    }

    public String getName(){
	return name;
    }

    public String getValue(){
	return contents;
    }

    public void setName(String newname){
	this.name = newname;
    }

    public void setValue(String value){
	this.contents = value;
    }

    public String toString(){
	return this.contents;
    }

}