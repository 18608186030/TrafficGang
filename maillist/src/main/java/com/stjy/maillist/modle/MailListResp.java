package com.stjy.maillist.modle;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class MailListResp {
    private List<ListBeanXX> list;

    public List<ListBeanXX> getList() {
        return list;
    }

    public void setList(List<ListBeanXX> list) {
        this.list = list;
    }

    public static class ListBeanXX implements MultiItemEntity , Parcelable {
        private String title;
        private List<ListBeanX> list;

        protected ListBeanXX(Parcel in) {
            title = in.readString();
        }

        public static final Creator<ListBeanXX> CREATOR = new Creator<ListBeanXX>() {
            @Override
            public ListBeanXX createFromParcel(Parcel in) {
                return new ListBeanXX(in);
            }

            @Override
            public ListBeanXX[] newArray(int size) {
                return new ListBeanXX[size];
            }
        };

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        @Override
        public int getItemType() {
            return 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(title);
        }

        public static class ListBeanX implements MultiItemEntity, Parcelable {
            private String title;
            private List<ListBean> list;

            protected ListBeanX(Parcel in) {
                title = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(title);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<ListBeanX> CREATOR = new Creator<ListBeanX>() {
                @Override
                public ListBeanX createFromParcel(Parcel in) {
                    return new ListBeanX(in);
                }

                @Override
                public ListBeanX[] newArray(int size) {
                    return new ListBeanX[size];
                }
            };

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            @Override
            public int getItemType() {
                return 1;
            }

            public static class ListBean implements MultiItemEntity , Parcelable{
                private String title;

                protected ListBean(Parcel in) {
                    title = in.readString();
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(title);
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                    @Override
                    public ListBean createFromParcel(Parcel in) {
                        return new ListBean(in);
                    }

                    @Override
                    public ListBean[] newArray(int size) {
                        return new ListBean[size];
                    }
                };

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                @Override
                public int getItemType() {
                    return 2;
                }
            }
        }
    }
}
