package com.stjy.baselib.bean.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author acb
 * @date 2018/7/13 16:37
 * @ClassName ConfigH5Bean
 * @Description <H5链接>
 */
public class ConfigH5Bean implements Parcelable {


    @SerializedName("list")
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {

        private int image;
        @SerializedName("name")
        private String name;
        @SerializedName("code")
        private String code;
        @SerializedName("value")
        private String value;

        public ListBean(int image, String name, String value) {
            this.image = image;
            this.name = name;
            this.value = value;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.image);
            dest.writeString(this.name);
            dest.writeString(this.code);
            dest.writeString(this.value);
        }

        protected ListBean(Parcel in) {
            this.image = in.readInt();
            this.name = in.readString();
            this.code = in.readString();
            this.value = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    public ConfigH5Bean() {
    }

    protected ConfigH5Bean(Parcel in) {
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<ConfigH5Bean> CREATOR = new Creator<ConfigH5Bean>() {
        @Override
        public ConfigH5Bean createFromParcel(Parcel source) {
            return new ConfigH5Bean(source);
        }

        @Override
        public ConfigH5Bean[] newArray(int size) {
            return new ConfigH5Bean[size];
        }
    };
}
