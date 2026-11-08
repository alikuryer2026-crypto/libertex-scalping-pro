package uz.scalping.pro;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.*;
import android.text.InputType;
public class MainActivity extends Activity {
  LinearLayout root; EditText entry,stop,target,balance,risk; TextView result;
  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    ScrollView scroll=new ScrollView(this);
    root=new LinearLayout(this);root.setOrientation(1);root.setPadding(36,40,36,32);root.setBackgroundColor(Color.rgb(15,17,22));
    scroll.addView(root);setContentView(scroll);
    label("SCALPING PRO",27,Color.WHITE);
    label("XAUUSD · M5 · DEMO / OFFLINE",15,0xFFAAAAAA);
    label("Oxirgi tarixiy kotirovka: $4,070.76",21,Color.WHITE);
    label("2026-11-06 21:59:59 UTC · Jonli narx EMAS",13,0xFFFF7777);
    label("SIGNAL: WAIT — bozor ma'lumoti eskirgan",17,0xFFFFCC66);
    label("BUY: M5 $4,072.26 ustida yopilib, retest tasdiqlansa",15,Color.WHITE);
    label("SELL: M5 $4,062.93 ostida yopilib, retest tasdiqlansa",15,Color.WHITE);
    label("Bu ilova real vaqtda savdo signalini bermaydi.",13,0xFFAAAAAA);
    label("Risk / Reward kalkulyatori",22,Color.WHITE);
    entry=field("Kirish narxi", "4073");stop=field("Stop-loss", "4068");target=field("Take-profit", "4083");
    balance=field("Hisob balansi ($)", "100");risk=field("Risk (%)", "1");
    Button calc=new Button(this);calc.setText("HISOBLASH");calc.setTextColor(Color.WHITE);
    calc.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFCD2430));root.addView(calc);
    result=label("Natija shu yerda chiqadi",17,Color.WHITE);
    calc.setOnClickListener(v->calculate());
    label("Ogohlantirish: spread, komissiya, slippage va broker kontrakt hajmi hisobga olinmagan. Savdo avtomatik ochilmaydi.",13,0xFFAAAAAA);
  }
  TextView label(String s,int size,int color) {
    TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setPadding(0,12,0,12);root.addView(t);return t;
  }
  EditText field(String hint,String value){
    label(hint,14,0xFFAAAAAA);
    EditText e=new EditText(this);e.setText(value);e.setSingleLine(true);e.setTextColor(Color.WHITE);
    e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);root.addView(e);return e;
  }
  void calculate(){
    try{
      double e=Double.parseDouble(entry.getText().toString()),s=Double.parseDouble(stop.getText().toString()),t=Double.parseDouble(target.getText().toString());
      double b=Double.parseDouble(balance.getText().toString()),r=Double.parseDouble(risk.getText().toString());
      double loss=Math.abs(e-s),gain=Math.abs(t-e);
      if(loss==0||b<=0||r<=0||r>100||!((s<e&&t>e)||(s>e&&t<e))){result.setText("Narxlar yoki risk noto'g'ri. SL va TP qarama-qarshi tomonda bo'lishi kerak.");return;}
      double maxLoss=b*r/100;
      result.setText(String.format(java.util.Locale.US,"Risk: $%.2f\nReward: 1:%.2f\nNazariy hajm: %.4f oz",maxLoss,gain/loss,maxLoss/loss));
    }catch(Exception ex){result.setText("Sonlarni to'g'ri kiriting.");}
  }
}