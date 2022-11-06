package org.example;

public class Request {
    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public void setDate(String date) {
        this.date = date;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    private String date;
    private int sum;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                '}';
    }
}
