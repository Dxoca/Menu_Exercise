package cn.dxoca.menu_exercise;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;
// android获取网页接口JSON数据 https://blog.csdn.net/lpcrazyboy/article/details/80296627
public class Hitokoto {
    private int id;
    private String hitokoto, from, creator;
    final String[] html = new String[1];
    Hitokoto() { }
    /**
     * @param text_hitokoto
     * @param text_from
     * @param text_temp
     * @param x             句子类型
     */
    Hitokoto(final TextView text_hitokoto, final TextView text_from, final TextView text_temp, final char x) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    html[0] = HtmlService.getHtml("https://v1.hitokoto.cn/?encode=json&c=" + x);
                    JSONObject obj = new JSONObject(html[0]);
                    Log.i("jsonData", html[0]);
                    final Hitokoto hitokoto = new Hitokoto(obj.getInt("id"), obj.getString("hitokoto"), obj.getString("from"), obj.getString("creator"));
//                    Hitokoto hitokoto = new Hitokoto();
//                    hitokoto.setHitokoto(obj.getString("hitokoto"));
//                    hitokoto.setFrom(obj.getString("from"));
//                    hitokoto.setCreator(obj.getString("creator"));
//                    hitokoto.setId(obj.getInt("id"));
                    System.out.println(x + ":" + hitokoto.getHitokoto() + "   ————" + hitokoto.getFrom());
                    text_hitokoto.post(new Runnable() {//拉出线程队列
                        @Override
                        public void run() {
//                            text_hitokoto.setText("『 " + hitokoto.getHitokoto() + " 』");
                            text_temp.setText(hitokoto.getHitokoto());
                            text_hitokoto.setText(text_temp.getText());
                            text_from.setText(" —— 「" + hitokoto.getFrom() + "」");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    Hitokoto(int id, String hitokoto, String from, String creator) {
        setId(id);
        setCreator(creator);
        setFrom(from);
        setHitokoto(hitokoto);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public String getFrom() {
        return from;
    }

    public String getCreator() {
        return creator;
    }
}
