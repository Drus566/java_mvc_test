package iplm.mvc.helpers.details;

import iplm.utility.StringUtility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchQueryParserHelper {
    Pattern strong_param = Pattern.compile("^\\+[a-zA-Z0-9\\.]+$");
    Pattern param_name = Pattern.compile("^[a-zA-Z0-9\\.]+$");
    Pattern param_string_value = Pattern.compile("^\"[a-zA-Z0-9\\.]+\"$");


    public SearchQueryParserHelper() {}
    // + - не трогаем, главное чтобы не было 2 подряд
    // \'
    // \\! \\^ \\{ \\} \\[ \\] \\: \\~ \\"
    // . % @ ? # $ & ` = ; < > ,

//    [Материал="олово",длина=3(2)] - детали где параметр материал = олово и длина = 3 с допуском 2 (если допуск без знака, то ищет в +2 и в -2)
//            [] - детали где нет параметров
//[!] - детали, где есть параметры
//    напр[!] - деталь в имени, децимальном номере или описании есть слово "напр" и у нее есть хотя бы один параметр
//    пластина[] - деталь в имени, дец. ном. или описании есть слово "пластина" и у нее есть хотя бы один параметр
//    шпилька[материал="золото",длина=5.3(3)] - деталь в имени, описании, дец. номере есть слово "шпилька" и у нее есть хотя бы один параметр.
//    шпилька[+материал,+длина] - детали под названием "шпилька", в которых обязательно должны быть указанные параметры - материал, длина
//[Длина=2(+2.33)] - поиск длины равной 2 с допуском в +2.3 т.к. значения округляются до десятых, итого идет поиск в диапазоне [2 - 4.3]
//            [Длина=2(-1.6)] - поиск длины равной 2 с допуском -1.6 т.е. в диапазоне с [1.4 - 2]
//
//    Пример поиска дубликатов на все детали
//[длина=20(200),высота=20(30)]

//    private final char[] spec_symbols = { '!', '^', '{', '}', '[', ']', ':', '~', '"' };
    private final char[] spec_symbols = { '!', '^', '{', '}', ':', '~', '"', '+', '-' };
    private final char[] n_spec_symbols = { '\'', '\\' };

    public String escapeQuery(String query) {
        if (query.trim().isEmpty()) return query;
        String[] q = query.split("\\[");
        String n_query = q[0];

        char[] chars = n_query.toCharArray();
        StringBuilder escape_query = new StringBuilder();
        for (char c : chars) {
            if (c == n_spec_symbols[0] || c == n_spec_symbols[1]) {
                escape_query.append("\\");
                escape_query.append(c);
                continue;
            }
            for (char s_c : spec_symbols) {
                if (c == s_c) {
                    escape_query.append("\\\\");
                    break;
                }
            }
            escape_query.append(c);
        }
        String result = escape_query.toString();
        if (q.length > 1) result = result + "[" + q[1];
        return result;
    }

    public boolean isValidQuery(String query) {
        if (query.isEmpty()) return true;

        // Если количество символов параметров много
        if (StringUtility.getCountSymbols(query, ']') > 1 || StringUtility.getCountSymbols(query, '[') > 1) return false;

        // Если есть открывающая скобка, то должна быть закрывающая
        int f_param_ch = query.indexOf('[');
        if (f_param_ch != -1) {
            int s_param_ch = query.lastIndexOf(']');
            if (!(f_param_ch != -1 && s_param_ch != -1 && f_param_ch < s_param_ch)) return false;
        }
        // Если есть закрывающая, но нет открывающей
        if (f_param_ch == -1 && query.lastIndexOf(']') != -1) return false;

        // Парсинг параметров внутри []
        String params = StringUtility.getBetweenChars(query, '[', ']');
        if (params != null && !params.trim().isEmpty()) {
            params = params.trim();
            // [!]
            if (params.equals("!")) return true;
            // [+материал,+длина]
            else if (params.charAt(0) == '+') {
                boolean result = true;
                String[] p_list = params.split(",");
                for (String p : p_list) {
                    Matcher matcher = strong_param.matcher(p);
                    if (!matcher.matches()) {
                        result = false;
                        break;
                    }
                }
                if (!result) return result;
            }
            // [материал="золото",длина=5.3(3)]
            else {
                boolean result = true;
                String[] p_list = params.split(",");
                for (String p : p_list) {
                    if (p.trim().isEmpty()) {
                        result = false;
                        break;
                    }
                    String[] kv = p.trim().split("=");
                    if (kv.length < 2) {
                        result = false;
                        break;
                    }
                    String key = kv[0].trim();
                    String value = kv[1].trim();

                    Matcher matcher = param_name.matcher(key);
                    if (!matcher.matches()) {
                        result = false;
                        break;
                    }

                    // Если это строка
                    if (value.trim().charAt(0) == '\"') {
                        matcher = param_string_value.matcher(value);
                        if (!matcher.matches()) {
                            result = false;
                            break;
                        }
                    }
                    // Если это число
                    else {
                        boolean brackets = false;

                        // Если количество символов скобок много
                        if (StringUtility.getCountSymbols(value, ')') > 1 || StringUtility.getCountSymbols(query, '(') > 1) {
                            result = false;
                            break;
                        }
                        // Если есть открывающая скобка, то должна быть закрывающая
                        int ch1 = value.indexOf('(');
                        if (ch1 != -1) {
                            int ch2 = value.lastIndexOf(')');
                            if (ch2 != -1 && ch1 != -1 && ch1 < ch2) brackets = true;
                            else {
                                result = false;
                                break;
                            }
                        }
                        // Если есть закрывающая, но нет открывающей
                        if (ch1 == -1 && value.lastIndexOf(')') != -1) {
                            result = false;
                            break;
                        }

                        if (brackets) {
                            String in_brackets = StringUtility.getBetweenChars(value, '(', ')');
                            if (in_brackets == null || in_brackets.isEmpty()) {
                                result = false;
                                break;
                            }

                            // пытаемся парсить float
                            try { Float.parseFloat(in_brackets); }
                            catch (Exception e) {
                                result = false;
                                break;
                            }

                            String v = StringUtility.cutToChar(value, '(');
                            // пытаемся парсить float
                            try { Float.parseFloat(v); }
                            catch (Exception e) {
                                result = false;
                                break;
                            }
                        }
                        else {
                            // пытаемся парсить float
                            try { Float.parseFloat(value); }
                            catch (Exception e) {
                                result = false;
                                break;
                            }
                        }
                    }
                }
                if (!result) return result;
            }
        }
        return true;
    }
}
