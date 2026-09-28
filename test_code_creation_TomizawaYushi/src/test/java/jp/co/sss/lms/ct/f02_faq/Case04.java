package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 * @author 冨澤 雄志
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// (初回ログイン済み受講生ユーザーの)ID, パスワードを入力
		webDriver.findElement(By.id("loginId")).clear();
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).clear();
		webDriver.findElement(By.id("password")).sendKeys("StudentAA00");

		// ログインボタンをクリック
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		// コース詳細画面が表示されるまで待つ
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.cssSelector("li.active"), "コース詳細"));

		// タイトルの判定処理
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 「機能」ドロップダウンをクリック
		webDriver.findElement(By.linkText("機能")).click();

		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));

		// 「ヘルプ」リンクがクリックできるようになるまで待ち、クリック
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("ヘルプ")))
				.click();

		// ヘルプ画面が表示されるまで待つ
		wait.until(ExpectedConditions.titleIs("ヘルプ | LMS"));

		// タイトルの判定処理
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「よくある質問」リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();

		// ウィンドウのハンドルを取得、最新のもの([1])へ移動
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		// タイトルの判定処理
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

}
