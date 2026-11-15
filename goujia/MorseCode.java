import java.util.*;
/*A Morse code message is an encoding of a signal on a single line. The signal is split into tacts where each tact contains a short signal (...)
Each letter is encoded by a sequence of dots and dashes, for example:
A: .-.
B: ..-
C: -.-

Additionally pauses are used to encode:
character breaks: "xxx"
word breaks (spaces): "xxxxx"
Example message:

".-.xxx..-xxxxx-.-" => "AB C" */
public class Main {
    private static final String[] MORSE = {
        ".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
        "....", "..", ".---", "-.-", ".-..", "--", "-.",
        "---", ".--.", "--.-", ".-.", "...", "-", "..-",
        "...-", ".--", "-..-", "-.--", "--.."
    };


     public static String Decode(String signal,Map<String,Character> map){
      int n=signal.length();
      int i=0;
      StringBuilder result = new StringBuilder();
        StringBuilder code = new StringBuilder(); //关键是想到用一个StringBuilder code***
      while(i<n){
       char c=signal.charAt(i);
       if(c=='.'||c=='-'){
        code.append(c);
        i++;
       }
       else if(c=='x'){
          // 当前字母结束，查表解码
            appendLetter(result, code, map);
            int start=i;
            while(i<n&&signal.charAt(i)=='x'){
              i++;

            }
           int count=i-start;
           if(count==5){
          result.append(' ');
           }
           else if(count!=3){
         throw new IllegalArgumentException("非法长度"+count);

           }

       }
      else {
                throw new IllegalArgumentException("非法字符：" + c);
            }

      }
  
       // 处理末尾没有分隔符的最后一个字母
        appendLetter(result, code, map);

        return result.toString();
     }

    private static void appendLetter(
            StringBuilder result,
            StringBuilder code,
            Map<String, Character> map) {
              if(code.length()==0)
              return;
           Character letter=map.get(code.toString());
           if (letter == null) {
            throw new IllegalArgumentException("无法识别的编码：" + code);
        }
           result.append(letter);
           code.setLength(0); //重置StringBuilder***

            }
    public static void main(String[] args) {
      System.out.println("Hello, World!");
      Map<String,Character> map=new HashMap<>();
      for(int i=0;i<MORSE.length;i++){
        map.put(MORSE[i],(char)(i+'A'));
      }
     String signal = ".-xxx-...xxxxx-.-.";
        System.out.println(Decode(signal, map)); // AB C

    String signal2 = "....xxx.xxx.-..xxx.-..xxx---"
                       + "xxxxx"
                       + ".--xxx---xxx.-.xxx.-..xxx-..";
        System.out.println(Decode(signal2, map)); // HELLO WORLD
    }
}

/*
1.once the first ' x' arrives, the preceding letter can already be output
第一个 x 到达时，就已经可以输出前面的字母
2. StringBuilder code = new StringBuilder(); //关键是想到用一个StringBuilder code***
3.   code.setLength(0); //重置StringBuilder***
*/