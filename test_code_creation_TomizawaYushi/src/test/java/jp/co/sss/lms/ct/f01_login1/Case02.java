package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 * @author 冨澤 雄志
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// トップページのURLにアクセスする
		goTo("http://localhost:8080/lms");

		// タイトルの判定処理
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// (DBに存在しない)ID, パスワードを入力
		webDriver.findElement(By.id("loginId")).clear();
		webDriver.findElement(By.id("loginId")).sendKeys("0000");
		webDriver.findElement(By.id("password")).clear();
		webDriver.findElement(By.id("password")).sendKeys("0000");

		// ログインボタンをクリック
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		// 「ログインに失敗しました。」が含まれるエラーメッセージが表示されるまで待つ
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("span.help-inline.error"),
				"ログインに失敗しました。"));

		// エラーメッセージの判定処理
		assertEquals("* ログインに失敗しました。", webDriver.findElement(By.cssSelector("span.help-inline.error")).getText());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

}
