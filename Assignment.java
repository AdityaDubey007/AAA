package rahulshettyacademy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.amazon.in");

		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("Ig soundbar");
		searchBox.submit();
		List<WebElement> productElements = driver.findElements(By.xpath(
				"//div[contains(@class, 's-main-slot')]//div[contains(@class,'s-result-item') and contains(@class,'s-asin')]"));

		List<Product> products = new ArrayList<>();
		for (WebElement productElement : productElements) {
			String productName = productElement.findElement(By.xpath(
					".//span[contains(@class, 'a-size-medium a-color-base a-text-normal')]"))
					.getText();
			System.out.println(productName);
			String priceString = productElement
					.findElement(By.xpath("//span[@class='a-price-whole']"))
					.getText().replace(",", "").trim();
			System.out.println(priceString);
			int price = Integer.parseInt(priceString);
			Product product = new Product(productName, price);
			products.add(product);
		}
		products.sort(Comparator.comparingInt(Product::getPrice));
		for (Product product : products) {
			System.out.println(product.getName() + "    " + product.getPrice());

		}
	}

	public static class Product {
		private String name;
		private int price;

		public Product(String name, int price) {
			this.name = name;
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public int getPrice() {
			return price;
		}
	}
}
