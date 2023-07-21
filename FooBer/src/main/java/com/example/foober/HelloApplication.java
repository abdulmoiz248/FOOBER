package com.example.foober;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Scanner;

public class HelloApplication extends Application {
    int pos=0;
    int rescount=0;
    int drivercount=0;
    int usercount=0;
    File res=new File("src/res.txt");
    File drive=new File("src/driver.txt");
    User users[]=new User[50];
    Resturant resturant[]=new Resturant[50];
    Driver driver[]=new Driver[50];
    ObservableList<Items> cart=FXCollections.observableArrayList();
    GridPane g3=new GridPane();
    Scene s3=new Scene(g3,700,700);
    GridPane g=new GridPane();
    Scene s=new Scene(g,700,700);

    @Override
    public void start(Stage stage) throws IOException {

      for(int i=0;i<users.length;i++) {
          users[i] = new User();
          driver[i]=new Driver();
          resturant[i]=new Resturant();
      }
        readuser();
        readres();
        readdriver();
        stage.setTitle("FOOBER");


        g.setVgap(10);
        g.setHgap(10);
        g.setAlignment(Pos.CENTER);


        //main screen
        FileInputStream fileInputStream=new FileInputStream("src/Foober.jpeg");
        Image foober=new Image(fileInputStream);
        ImageView imageView=new ImageView(foober);
        ColumnConstraints c=new ColumnConstraints();
        c.setHalignment(HPos.CENTER);
        g.getColumnConstraints().add(c);
        imageView.setFitHeight(100);
        imageView.setFitWidth(130);
        g.add(imageView,3,0);

        Button register=new Button("Register");
        Button login=new Button("Login");
        VBox v=new VBox(imageView,register,login);
        v.setSpacing(10);
        v.setAlignment(Pos.CENTER);
        g.add(v,0,0);
        stage.setScene(s);

        //reg screen
        GridPane g1=new GridPane();
        Scene s1=new Scene(g1,700,700);
        g1.setVgap(10);
        g1.setHgap(10);
        g1.setAlignment(Pos.CENTER);
        register.setOnAction(e->stage.setScene(s1));
        Font f=new Font(25);
        Label reg=new Label("Welcome To FOOBER");
        reg.setStyle("-fx-font-weight: bold;");
        reg.setFont(f);
        g1.add(reg,3,0);
        g1.add(new Label("Username "),2,2);
        TextField regname=new TextField();
        g1.add(regname,3,2);
        g1.add(new Label("Password "),2,3);
        PasswordField regpass=new PasswordField();
        g1.add(regpass,3,3);
        Button cancelreg=new Button("Cancel");
        cancelreg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(s);
                regname.clear();
                regpass.clear();
            }
        });
        Label errorreg=new Label();
       Button regbutton=new Button("Register");
       regbutton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
                 if(!regname.getText().isEmpty()) {
                     if (!regpass.getText().isEmpty()) {
                         int check=0;
                         try {
                             for (int i = 0; i < usercount; i++) {
                                 if (users[i].register.getName().equals(regname.getText())) {
                                     check++;
                                 }
                             }
                         }catch (Exception e)
                         {
                             System.out.println(e);
                         }
                         if(check==0)
                         {
                             users[usercount].register.setName(regname.getText());
                             users[usercount].register.setPassword(regpass.getText());
                             try {
                                 writeres(regname.getText(),regpass.getText());
                             } catch (IOException e) {
                                 System.out.println(e);
                             }
                             usercount++;
                             regname.clear();
                             regpass.clear();
                             stage.setScene(s);
                             errorreg.setText("");

                         }else {
                             errorreg.setText("Username Exist..!!");
                         }

                     } else {
                         errorreg.setText("Enter Password");
                     }
                 }
                 else
                 {
                     errorreg.setText("Enter Username");
                 }
           }
       });
      HBox regh=new HBox(regbutton,cancelreg);
      g1.add(regh,3,4);
      g1.add(errorreg,3,5);


      //log user
        GridPane g2=new GridPane();
        Scene s2=new Scene(g2,700,700);
        g2.setVgap(10);
        g2.setHgap(10);
        g2.setAlignment(Pos.CENTER);

       login.setOnAction(e->stage.setScene(s2));
        Font f1=new Font(25);
        Label log=new Label("Welcome To FOOBER");
        log.setFont(f1);
        log.setStyle("-fx-font-weight: bold;");
        g2.add(log,3,0);
        g2.add(new Label("Username "),2,2);
        TextField logname=new TextField();
        g2.add(logname,3,2);
        g2.add(new Label("Password "),2,3);
        PasswordField logpass=new PasswordField();
        g2.add(logpass,3,3);
        Button cancelog=new Button("Cancel");
        cancelog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(s);
                logname.clear();
                logpass.clear();
            }
        });
        Button savelog=new Button("Login");

        ColumnConstraints c1=new ColumnConstraints();
        c1.setHalignment(HPos.CENTER);
        g3.getColumnConstraints().add(c1);

        Label error=new Label();
        savelog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int check=0;
                for (int i = 0; i < usercount; i++) {
                    if (users[i].register.getName().equals(logname.getText())) {
                        if (users[i].register.getPassword().equals(logpass.getText())) {
                            stage.setScene(s3);
                            pos=i;
                            buttonadder(g3,stage);
                            error.setText("");
                            break;
                        }else
                        {
                            check++;
                        }
                    }else check++;
                }
                if(check!=0)
                {
                    error.setText("Incorrect Username or Password");
                }
            }
        });
        HBox logh=new HBox(savelog,cancelog);
        g2.add(logh,3,4);
        g2.add(error,3,5);

//rest
        g3.setVgap(10);
        g3.setHgap(10);
        g3.setAlignment(Pos.CENTER);
        Font font=new Font(20);
        Label select=new Label("Select Your");
        select.setStyle("-fx-font-weight: bold;");
        Label select2=new Label("Desired");
        select2.setStyle("-fx-font-weight: bold;");
        Label   Rest=new Label(" Restaurant");
        Rest.setStyle("-fx-font-weight: bold;");
        Rest.setFont(font);
        select2.setFont(font);
        select.setFont(font);
        g3.add(select,2,0);
        g3.add(select2,3,0);
        g3.add(Rest,4,0);
        Button logout=new Button("<- LOG OUT");
        g3.add(logout,6,0);
        logout.setOnAction(e-> stage.setScene(s));
        stage.show();
    }


    public void buttonadder(GridPane g,Stage stage)
    {
        int count=0;
        Font f=new Font(12);
        Button b[]=new Button[50];
        for(int i=0;i< b.length;i++)
            b[i]=new Button();
        int row=3;
        int col=2;
        for(Resturant r:resturant)
        {
            if(r.getName()!=null) {
                b[count].setText(r.getName());
                b[count].setFont(f);
                g.add(b[count], col, row);
                int finalCount = count;
                b[count].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        menu(finalCount,stage);
                    }
                });
                count++;
                col+=2;
               if(col==6)
               {
                   row++;
                   col=2;
               }

            }
        }
    }
    public void menu(int index,Stage s)
    {
        Label price[]=new Label[50];
        Label items[]=new Label[50];
        Button plus[]=new Button[50];
        Button minus[]=new Button[50];
        Label quantity[]=new Label[50];
        HBox h[]=new HBox[50];
        int itemcount=0;
        int pluscount=0;
        int minuscount=0;
        int quancount=0;
        int row=3;
        int col=2;
        int hc=0;
        int pricec=0;
        ObservableList<String> flavour= FXCollections.observableArrayList();
        flavour.addAll("Chicken Tikka","Chicken Fajita","Pineapple");
        ComboBox<String> flavours=new ComboBox<>(flavour);
        for(int i=0;i<items.length;i++)
        {
            items[i]=new Label();
            plus[i]=new Button("+");
            minus[i]=new Button("-");
            quantity[i]=new Label("0");
            h[i]=new HBox();
            price[i]=new Label();
        }
        GridPane g=new GridPane();
        Scene sc=new Scene(g,700,700);
        g.setVgap(10);
        g.setHgap(10);
        g.setAlignment(Pos.CENTER);
        s.setScene(sc);
        Font f=new Font(25);
        Font f1=new Font(20);
        Label order=new Label("Select Your");
        Label order2=new Label("Order");

        order.setFont(f);
        order2.setFont(f);
        g.add(order,2,1);
        g.add(order2,3,1);
        Button back=new Button("<- BACK");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                s.setScene(s3);
                for(int i=0;i<quantity.length;i++)
                    quantity[i].setText("0");
            }
        });

        g.add(back,5,1);
        Button cartb=new Button("CART \uD83D\uDED2");
        g.add(cartb,6,1);
        Resturant r=resturant[index];
        Label head=new Label(r.getName());
        head.setStyle("-fx-font-weight: bold;");
        head.setFont(new Font(30));
        g.add(head,4,0);
        for(Items i:r.menu.items)
        {
             if(i.getName()!=null)
             {
                 final int[][] quan = {{0}};
                 Font f2=new Font(13);
                 items[itemcount].setText(i.getName());
                 items[itemcount].setFont(f2);
                 price[pricec].setText(String.valueOf(i.getPrice()));
                 price[pricec].setStyle("-fx-font-weight: bold;");
                 g.add(price[pricec],6,row);
                 pricec++;
                 if(i.getName().equals("Pizza"))
                 {
                     g.add(flavours,4,row);
                 }
                 g.add(items[itemcount],col,row);

                 int finalQuancount = quancount;
                 plus[pluscount].setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent actionEvent) {
                         quantity[finalQuancount].setText(String.valueOf(++quan[0][0]));

                     }
                 });

                 minus[minuscount].setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent actionEvent) {
                         int q=quan[0][0];
                       if(q!=0)
                         {
                           quan[0][0]--;
                             quantity[finalQuancount].setText(String.valueOf(quan[0][0]));
                         }
                     }
                 });
                h[hc].getChildren().addAll(plus[pluscount],quantity[quancount],minus[minuscount]);
                g.add(h[hc],5,row);
                h[hc].setSpacing(10);
                // col=2;
                 row++;
                 itemcount++;
                 pluscount++;
                 quancount++;
                 minuscount++;
                 hc++;
             }
        }
        Button addtocart=new Button("Add to Cart");
        g.add(addtocart,5,(row+1));
        addtocart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int loopvary=0;
                for(Label l:quantity)
                {
                    if(!l.getText().equals(null))
                    {
                        int j= Integer.parseInt(l.getText());
                            for(int i=0;i<j;i++)
                            {
                                Items items1=r.menu.items.get(loopvary);
                                cart.add(items1);
                            }
                        loopvary++;
                    }
                }

            }
        });

        cartb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
              cartscreen(s,sc);
            }
        });
    }
    public void cartscreen(Stage stage,Scene scene) throws ConcurrentModificationException
    {

        GridPane g=new GridPane();
        Scene s=new Scene(g,700,700);
        stage.setScene(s);
        Button back=new Button("<- BACK");
        back.setOnAction(e-> stage.setScene(scene));
        TableView<Items> tableView=new TableView<>(cart);
        TableColumn<Items,String> name=new TableColumn<>("Name");
        name.setPrefWidth(150);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Items,Double>  price=new TableColumn<>("Price");
        price.setPrefWidth(100);
        tableView.setPrefWidth(250);
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        g.setAlignment(Pos.CENTER);
        tableView.getColumns().addAll(name,price);
        g.add(tableView,3,0);
        g.setVgap(1);
        final int[] total = {0};
        for(Items i:cart)
            total[0] +=i.getPrice();
        Button placeorder=new Button("Place Order Rs."+ total[0]);
        int finalTotal = total[0];
        placeorder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(finalTotal !=0) {
                    for (Items i : cart)
                        users[pos].order.items.add(i);
                    calldriver(stage);
                }
            }
        });
        ComboBox<Items> itemname=new ComboBox<>(cart);
        Button remove=new Button("Remove ");
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    cart.remove(itemname.getSelectionModel().getSelectedIndex());
                    total[0]=0;
                for(Items i:cart)
                    total[0] +=i.getPrice();
                placeorder.setText("Place Order Rs."+ total[0]);
            }
        });
        HBox h=new HBox(back,placeorder);
        h.setSpacing(15);
        g.add(h,3,5);
        HBox h1=new HBox(itemname,remove);
        h1.setSpacing(10);
        g.add(h1,3,6);
    }
    public void calldriver(Stage stage)
    {
        GridPane g=new GridPane();
        Scene s1=new Scene(g,700,700);
        g.setAlignment(Pos.CENTER);
        g.setVgap(10);
        g.setHgap(10);
        stage.setScene(s1);
        ToggleGroup toggleGroup=new ToggleGroup();
        RadioButton auto=new RadioButton("Auto");
        RadioButton car=new RadioButton("Car");
        RadioButton bike=new RadioButton("Bike");
        bike.setToggleGroup(toggleGroup);
        auto.setToggleGroup(toggleGroup);
        car.setToggleGroup(toggleGroup);
        ObservableList<Driver> bikedriver=FXCollections.observableArrayList();
        ObservableList<Driver> autodriver=FXCollections.observableArrayList();
        ObservableList<Driver> cardriver=FXCollections.observableArrayList();
        ComboBox<Driver> comboBox=new ComboBox<>();
        for(int i=0;i<drivercount;i++)
        {

            if (driver[i].getVehicle().equals("Bike"))
                bikedriver.add(driver[i]);
            else if (driver[i].getVehicle().equals("Auto")) {
                autodriver.add(driver[i]);
            } else if (driver[i].getVehicle().equals("Car")) {
                cardriver.add(driver[i]);
            }
        }
        ObservableList<String> location=FXCollections.observableArrayList();
        ComboBox<String> loc=new ComboBox<>(location);
        location.setAll("Faisal Town","LDA Avenue","DHA","Bahira Town","Kalma Chowk","Barkat Market","Gulberg","Main Market","Mall Road","Fortress");
          Button search=new Button("Search For Driver");
     search.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent actionEvent) {
             if(bike.isSelected()) {
                 comboBox.setItems(bikedriver);
                  for(Driver d:bikedriver)
                  {
                      d.calculatefare(loc.getSelectionModel().getSelectedIndex());
                  }
             } else if (auto.isSelected()) {
                 comboBox.setItems(autodriver);
                 for(Driver d:autodriver)
                 {
                     d.calculatefare(loc.getSelectionModel().getSelectedIndex());
                 }
             } else if (car.isSelected()) {
                 comboBox.setItems(cardriver);
                 for(Driver d:cardriver)
                 {
                     d.calculatefare(loc.getSelectionModel().getSelectedIndex());
                 }
             }

         }
     });
       HBox h=new HBox(bike,auto,car);
       Label head=new Label("Select Your Driver");

       g.add(new Label("Select Your Location"),3,2);
       g.add(loc,4,2);
       head.setStyle("-fx-font-weight: bold;");
       g.add(head,0,0);
       head.setFont(new Font(30));
       g.add(new Label("Select Your Vehicle"),3,3);
       g.add(h,4,3);
       g.add(search,4,4);
       g.add(new Label("Available Drivers"),3,5);
       g.add(comboBox,4,5);

       Button bookride=new Button("Book");

       g.add(bookride,4,7);
       bookride.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
              if(comboBox.getValue()!=null)
                  if(loc.getValue()!=null)
                  {
            Alert end=new Alert(Alert.AlertType.INFORMATION);
            end.setTitle("Driver on its way..!!");
            end.setContentText("Driver will pick you up soon..!!");
            stage.setScene(s);
            end.show();

                  }
           }
       });
    }
    public void readdriver() throws FileNotFoundException {
        FileReader fw=new FileReader("src/driver.txt");
        BufferedReader bf=new BufferedReader(fw);
        while (true) {
            try {
                String temp = bf.readLine();
                String tempa[]=temp.split("%");
                driver[drivercount].setName(tempa[0].trim());
                driver[drivercount].setVehicle(tempa[1].trim());
                driver[drivercount].setBasefare(Double.parseDouble(tempa[2]));
                drivercount++;
            }catch (Exception e)
            {
                break;
            }
        }
    }
    public void readuser() throws IOException
    {
        FileReader fw=new FileReader("src/data.txt");
      BufferedReader bf=new BufferedReader(fw);
      while (true) {
          try {
              String temp = bf.readLine();
              String tempa[]=temp.split("%");
              users[usercount].register.setName(tempa[0].trim());
              users[usercount].register.setPassword(tempa[1]);
              usercount++;
          }catch (Exception e)
          {
              break;
          }
      }


    }
    public void writeres(String username,String password) throws IOException {
        FileWriter fw=new FileWriter("src/data.txt",true);
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(username+"%"+password);
        bw.newLine();
        bw.close();
    }

    public void readres() throws FileNotFoundException {
        Scanner read=new Scanner(res);
        while (read.hasNext())
        {
           String temp= read.nextLine();
           String tempa[]=temp.split("%");
           resturant[rescount].setName(tempa[0]);
           for(int i=1;i<tempa.length;i++)
           {
               Items items=new Items();
               items.setName(tempa[i]);
               i++;
               items.setPrice(Double.parseDouble(tempa[i]));
               resturant[rescount].menu.items.add(items);
           }
           rescount++;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}