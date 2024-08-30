package com.xbstar.esl.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Create by :kira
 * @CreateTime :2020/2/4:19:23
 * @Version : v1.0.1
 * @Describe : 接口返回对象格式
 */
public class AlvesJSONResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    //private String ok;	// 不使用

    private String token;

    public static AlvesJSONResult build(Integer status, String msg, Object data) {
        return new AlvesJSONResult(status, msg, data);
    }

    public static AlvesJSONResult ok(Object data) {
        return new AlvesJSONResult(data);
    }

    public static AlvesJSONResult pageOk(Object list, Long count) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        return ok(map);
    }

    /**
     * 添加  sessionId 返回给手机端用户的
     *
     * @param data
     * @param token
     * @return
     */
    public static AlvesJSONResult ok(Object data, String token) {
        return new AlvesJSONResult(data, token);
    }

    public static AlvesJSONResult ok() {
        return new AlvesJSONResult(null);
    }

    /**
     * 常规错误 status 500
     *
     * @param msg
     * @return
     */
    public static AlvesJSONResult errorMsg(String msg) {
        return new AlvesJSONResult(500, msg, null);
    }
    /** public static AlvesJSONResult errorMsg(String msg,Object data){
     return new AlvesJSONResult(500,msg,data);
     }*/
    /**
     * 带数据的错误 status 501
     *
     * @param data
     * @return
     */
    public static AlvesJSONResult errorData(Object data) {
        return new AlvesJSONResult(501, "error", data);
    }

    /**
     * token异常错误 status 502
     *
     * @param msg
     * @return
     */
    public static AlvesJSONResult errorTokenMsg(String msg) {
        return new AlvesJSONResult(502, msg, null);
    }

    /**
     * 查询不到数据 ，无数据报错误 status 503
     *
     * @param msg
     * @return
     */
    public static AlvesJSONResult errorNullData(String msg) {
        return new AlvesJSONResult(503, msg, null);
    }

    public static AlvesJSONResult errorException(String msg) {
        return new AlvesJSONResult(555, msg, null);
    }


    public AlvesJSONResult() {

    }

    public AlvesJSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public AlvesJSONResult(Object data, String token) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
        this.token = token;
    }

    public AlvesJSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 将json结果集转化为   AlvesJSONResult  对象
     * 需要转换的对象是一个类
     *
     * @param jsonData
     * @param clazz
     * @return
     */
    public static AlvesJSONResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, AlvesJSONResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param clazz    对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonData) || clazz == null) {
            return null;
        }
        try {
            T t = MAPPER.readValue(jsonData, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将AlvesJSONResult对象转换成json字符串。
     */
    public static String ToJson(AlvesJSONResult data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static AlvesJSONResult format(String json) {
        try {
            return MAPPER.readValue(json, AlvesJSONResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Object是集合转化
     * * 				需要转换的对象是一个list
     *
     * @param jsonData
     * @param clazz
     * @return
     */
    public static AlvesJSONResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "AlvesJSONResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", token='" + token + '\'' +
                '}';
    }
}
