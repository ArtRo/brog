package cn.ndm.string;

import cn.ndm.collection.CollectionBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchStringUtil {

    public static final Pattern pattern = Pattern.compile("\\{([a-zA-Z0-9_\\-]+)\\}");


    public static String concat(Collection<?> c) {
        return concat(c, ",");
    }


    public static String concat(Collection<?> c, String spliter) {
        StringBuilder ret = new StringBuilder();
        if (c != null && c.size() > 0) {
            Iterator var3 = c.iterator();

            while(var3.hasNext()) {
                Object x = var3.next();
                ret.append(spliter).append(x.toString());
            }

            if (ret.length() > 0) {
                ret.deleteCharAt(0);
            }
        }

        return ret.toString();
    }
    public static String concat(Object... args) {
        if (args == null) {
            return "";
        } else if (args.length <= 0) {
            return "";
        } else {
            StringBuilder ret = new StringBuilder();
            Object[] var2 = args;
            int var3 = args.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object s = var2[var4];
                if (s == null) {
                    s = "null";
                }

                if (s.getClass().isArray()) {
                    Object[] ss = (Object[])((Object[])s);
                    ret.append("[");
                    Object[] var14 = ss;
                    int var16 = ss.length;

                    for(int var17 = 0; var17 < var16; ++var17) {
                        Object obj = var14[var17];
                        ret.append(CollectionBuilder.getString(obj)).append(",");
                    }
                    ret.append("]");
                } else if (Collection.class.isAssignableFrom(s.getClass())) {
                    Collection c = (Collection)s;
                    ret.append("[");
                    Iterator var13 = c.iterator();

                    while(var13.hasNext()) {
                        Object v = var13.next();
                        ret.append(CollectionBuilder.getString(v)).append(",");
                    }

                    ret.append("]");
                } else if (!Map.class.equals(s.getClass())) {
                    ret.append(s.toString());
                } else {
                    Map m = (Map)s;
                    Set<Map.Entry> set = m.entrySet();
                    ret.append("{");
                    Iterator var8 = set.iterator();

                    while(var8.hasNext()) {
                        Map.Entry e = (Map.Entry)var8.next();
                        ret.append(e.getKey()).append(":").append(e.getValue()).append(",");
                    }

                    ret.append("}");
                }
            }

            return ret.toString();
        }
    }

    public static String concat(String spliter, byte[] args) {
        if (args == null) {
            return "";
        } else if (args.length <= 1) {
            return args.length == 1 ? String.valueOf(args[0]) : "";
        } else {
            StringBuilder ret = new StringBuilder();
            int n = spliter.length();
            byte[] var4 = args;
            int var5 = args.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                byte s = var4[var6];
                ret.append(spliter).append(Byte.toUnsignedInt(s));
            }

            return ret.toString();
        }
    }

    public static String concat(String spliter, int[] args) {
        if (args == null) {
            return "";
        } else if (args.length <= 1) {
            return args.length == 1 ? String.valueOf(args[0]) : "";
        } else {
            StringBuilder ret = new StringBuilder();
            int n = spliter.length();
            int[] var4 = args;
            int var5 = args.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                int s = var4[var6];
                ret.append(spliter).append(s);
            }

            return ret.toString();
        }
    }

    public static String concat(String spliter, double[] args) {
        if (args == null) {
            return "";
        } else if (args.length <= 1) {
            return args.length == 1 ? String.valueOf(args[0]) : "";
        } else {
            StringBuilder ret = new StringBuilder();
            int n = spliter.length();
            double[] var4 = args;
            int var5 = args.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                double s = var4[var6];
                ret.append(spliter).append(s);
            }

            return ret.toString();
        }
    }

    public static String concat(String spliter, Object[] args) {
        if (args == null) {
            return "";
        } else if (args.length <= 1) {
            if (args.length == 1) {
                return args[0] == null ? "null" : args[0].toString();
            } else {
                return "";
            }
        } else {
            StringBuilder ret = new StringBuilder();
            int n = spliter.length();
            Object[] var4 = args;
            int var5 = args.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Object s = var4[var6];
                if (s == null) {
                     s = "null";
                    ret.append(spliter).append(s.toString());
                } else if (!s.getClass().isArray()) {
                    ret.append(spliter).append(s.toString());
                } else {
                    Object[] ss = (Object[])((Object[])s);
                    ret.append("[");
                    Object[] var9 = ss;
                    int var10 = ss.length;

                    for(int var11 = 0; var11 < var10; ++var11) {
                        Object obj = var9[var11];
                        ret.append(obj.toString()).append(",");
                    }

                    ret.append("]");
                }
            }

            if (ret.length() > n) {
                ret.delete(0, n);
            }

            return ret.toString();
        }
    }

    public static String concat(String format, String spliter, List<Object> args) {
        int n = args.size();
        if (n > 1) {
            int len = spliter.length();
            StringBuilder ret = new StringBuilder();
            Iterator var6 = args.iterator();

            while(var6.hasNext()) {
                Object arg = var6.next();
                Object[] p;
                if (arg instanceof List) {
                    List<Object> l = (List)arg;
                    p = l.toArray();
                } else {
                    p = new Object[]{arg};
                }

                String s = String.format(format, p);
                ret.append(spliter).append(s);
            }

            if (ret.length() > len) {
                ret.delete(0, len);
            }

            return ret.toString();
        } else {
            if (n == 1) {
                ;
            }

            return "";
        }
    }


    public static String genStr(String prefix, String target, Map<String, Object> param) {
        StringBuilder ret = new StringBuilder(prefix);
        Matcher m = pattern.matcher(target);
        int start = 0;
        if (param != null) {
            while(m.find(start)) {
                String k = m.group(1);
                if (!param.containsKey(k)) {
                    throw new IllegalArgumentException(concat("param ", k, " not set "));
                }

                String kk = param.get(k).toString();
                ret.append(target.subSequence(start, m.start()));
                ret.append(kk);
                start = m.end();
            }
        }

        if (start < target.length()) {
            ret.append(target.substring(start));
        }

        return ret.toString();
    }

    public static String menStr(String template,Map<String,Object>params){
        if(null == params || params.size() <=0|| StringUtils.isEmpty(template)){
            return "";
        }
        Matcher matcher = pattern.matcher(template);
        StringBuffer stringBuffer = new StringBuffer("");
        while (matcher.find()){
            String group = matcher.group(1);
            String str = (String)params.get(group);
            matcher.appendReplacement(stringBuffer,str);
        }
        return matcher.appendTail(stringBuffer).toString();
    }

}


