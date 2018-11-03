public class Work {
    public String flag;
    public String name;
    public String getTime;
    public String endTime;
    public int length;
    public int level;
    public int ratio;
    public int done;
    public Work(String flag,String name,int length,String getTime){
        this.flag=flag;
        this.name=name;
        this.length=length;
        this.getTime=getTime;
        this.done=0;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void setRatio(int ratio){
        this.ratio=ratio;
    }
    public void run(){
        if(done==length){
            flag="已完成";
            return;
        }
        if(done<length){
            done+=1;
            if(done==length){
                flag="已完成";
            }
        }
    }
    public void setFlag(String flag){
        this.flag=flag;
    }
    public void setEndTime(String endTime){
        this.endTime=endTime;
    }
}
