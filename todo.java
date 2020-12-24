import java.io.IOException;
import java.text.DateFormat;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

class todo
{
    DateFormat dateFormat=null;
    Date date=null;
    todo()
    {
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //get current date time with Date()
        date = new Date();
        System.out.print(dateFormat.format(date));
    }
    //Method to mark a todo task done and write it in done.txt
    public String doneTodo(int d)throws IOException
    {
        try{
            File file= new File("todo.txt");
            File file1=new File("done.txt");
            File file2=new File("temp.txt");
            file1.createNewFile();
            file2.createNewFile();
            BufferedReader reader=new BufferedReader(new FileReader(file));
            BufferedWriter writer1=new BufferedWriter(new FileWriter(file1));
            BufferedWriter writer=new BufferedWriter(new FileWriter(file2));
            int j=1;
            String line="";
            String done="";
            
            while((line=reader.readLine())!=null)
            {
                if(line!=null && j==d)
                {
                    int n=line.length();
                    for(int i=0;i<n;i++)
                        writer1.append(line.charAt(i));
                    writer1.append("\n");
                    done=line;
                }
                else
                {
                    writer.write(line+"\n");
                }
                j++;
            }
            writer.close();
            writer1.close();
            reader.close();
            file2.renameTo(file);
            return done;
        }
        catch(Exception e)
        {
            System.out.println("Input is not Correct");
            e.printStackTrace();
        }
        return "";
    }
    //method to add task
    public boolean addTodo(String todo)throws IOException
    {
        boolean flag=false;
        try{
            File file=new File("todo.txt");
            FileWriter files=new FileWriter("todo.txt",true);

            int len=todo.length();
            //Check if file Exists or not.If not exists Create a new File.
            file.createNewFile();

            for(int i=0;i<len;i++)
                files.append(todo.charAt(i));
            
            files.append("\n");
            
            flag=true;
            files.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return flag;
    }
    //method to delete todo task 
    public String delTodo(int d)
    {
        try{
            File file= new File("todo.txt");
            File file1=new File("new.txt");

            BufferedReader reader=new BufferedReader(new FileReader(file));
            BufferedWriter writer=new BufferedWriter(new FileWriter(file1));

            int j=1;
            String line="";
            String del="";
            while((line=reader.readLine())!=null)
            {
                if(line!=null && j!=d)
                {
                    writer.write(line+"\n");
                }
                else
                    del=line;
                j++;
            }
            reader.close();
            writer.close();
            file1.renameTo(file);
            return del;
        }
        catch(Exception e)
        {
            System.out.println("Input is not Correct");
            e.printStackTrace();
        }
        return null;
    }
    //method to get list of a file
    public static ArrayList<String> getList(File file)
    {
        //List to save Strings
        ArrayList<String> list=new ArrayList<String>();
        try{
            
            int ch;
            FileReader reader=new FileReader(file);
            String temp="";
            while((ch=reader.read()) != -1)
            {
                temp= temp+(char)ch;
                if((char)ch=='\n')
                {
                    list.add(temp);
                    temp="";
                }
            }
            reader.close();
        }
        catch(FileNotFoundException f)//In case file is not exist
        {
            System.out.println("Something went wrong");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    //list of todo tasks
    public void printToDoList()throws IOException
    {
        ArrayList<String> list=todo.getList(new File("todo.txt"));
        int len=list.size();
        for(int i=len-1;i>=0;i--)
        {
            System.out.print("["+(i+1)+"] "+list.get(i));
        }
    }/*Not Given in Specifications
    //list of done tasks
    public void printDoneList()throws IOException
    {
        ArrayList<String> list=todo.getList(new File("done.txt"));
        int len=list.size();
        for(int i=len-1;i>=0;i--)
        {
            System.out.print("["+(i+1)+"] "+list.get(i));
        }
    }*/
    //method to write report of completed and pending tasks
    public void report()
    {
        try{
            File todo=new File("todo.txt");
            File done=new File("done.txt");

            int to=0,don=0;
            Scanner sc=new Scanner(todo);
            while(sc.hasNextLine())
            {
                sc.nextLine();
                to++;
            }
            sc.close();
            sc=new Scanner(done);
            while(sc.hasNextLine())
            {   
                sc.nextLine();
                 don++;
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            //get current date time with Date()
            Date date = new Date();
            System.out.print(dateFormat.format(date));
            System.out.print(" Pending:"+to+" Completed:"+don);
            sc.close();
            System.out.println();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String args[])throws IOException
    {
        String cmd="";
        todo file=new todo();
        if(args.length==0)
            cmd="help";
        else
            cmd=args[0];
        try{
            switch (cmd.toLowerCase()) {
                //In case of add Command
                case "add":
                    if(args.length==1)//If only command entered without an input
                    {
                        System.out.println("Please give a Input");
                        break;
                    }
                    if(file.addTodo(args[1]))
                        System.out.println("Added todo: "+args[1]);
                    else
                        System.out.println("Something went wrong");
                    break;
                //In case of list Command
                case "ls":
                    file.printToDoList();
                    break;
                //In delete command
                case "del":
                    if(args.length==1)//If only command entered without an input
                    {
                        System.out.println("Please give a Input");
                        break;
                    }
                    //Converting string into int and passing int as argument
                    System.out.println("Deleted todo: "+file.delTodo(Integer.parseInt(args[1])));
                    break;
                //In case of done command
                case "done":
                    file.doneTodo(Integer.parseInt(args[1]));
                    System.out.println("Marked todo #"+args[1]+" as done");
                    break;
                //in case of report command
                case "report":
                    file.report();
                    break;
                //In case of Help or another commands including null String
                default:
                    System.out.println("Usage :-");
                    System.out.println("$ ./todo add \"todo item\"  #Add a new todo");
                    System.out.println("$ ./todo ls                 #Show remaining todo");
                    System.out.println("$ ./todo del NUMBER         #Delete a todo");
                    System.out.println("$ ./todo done NUMBER        #Complete a todo");
                    System.out.println("$ ./todo help               #Show Usage");
                    System.out.println("$$ ./todo report            #Statistics");
                    System.out.println(",,,,,");
                }
            }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}