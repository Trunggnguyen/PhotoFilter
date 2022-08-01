package com.example.photofilter.model;

public class ScreenIntroItem {

   String Hello ,Title,Description;
   int ScreenImg;

   public ScreenIntroItem(String hello, String title, String description, int screenImg) {
       Hello = hello;
       Title = title;
       Description = description;
       ScreenImg = screenImg;
   }

   public String getHello() {
       return Hello;
   }

   public void setHello(String hello) {
       Hello = hello;
   }

   public String getTitle() {
       return Title;
   }

   public void setTitle(String title) {
       Title = title;
   }

   public String getDescription() {
       return Description;
   }

   public void setDescription(String description) {
       Description = description;
   }

   public int getScreenImg() {
       return ScreenImg;
   }

   public void setScreenImg(int screenImg) {
       ScreenImg = screenImg;
   }
}
