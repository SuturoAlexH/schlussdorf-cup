# schlussdorf-cup-ui

### setups steps

   1. Add these two lines to the vm options of the run configuration
   
        --module-path libs/javafx-sdk/lib 
        
        --add-modules=javafx.controls,javafx.fxml,javafx.graphics,javafx.base
   
   2. Download javafx-sdk from https://gluonhq.com/products/javafx/
      and put it inside "libs/javafx-sdk"
      
   3. Copy the bin and lib folder from your java-jdk folder (C:\Program Files\Java\jdk-14.0.1) to "libs/jdk"
   
### build

Just run the build.bat file
