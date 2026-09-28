package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 * @author 冨澤 雄志
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// 表示件数を全件に変更
		Select select = new Select(webDriver.findElement(By.cssSelector(".form-control.input-sm")));
		select.selectByVisibleText("ALL");

		// 「【研修関係】」リンクをクリック
		webDriver.findElement(By.linkText("【研修関係】")).click();

		// カテゴリ検索結果に該当する質問がすべて表示されているかの判定処理
		List<WebElement> searchedQuestions = webDriver.findElements(By.cssSelector("[id^='question-h']"));

		for (WebElement searchedQuestion : searchedQuestions) {
			assertTrue(searchedQuestion.isDisplayed());
		}

		// 検索結果へ移動
		scrollTo(String.valueOf(webDriver.findElement(By.className("sorting_asc")).getLocation().getY()));

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// 質問をクリック
		webDriver.findElement(By.id("question-h[${status.index}]")).click();

		// 該当の質問に対する回答が表示されているかの判定処理
		assertTrue(webDriver.findElement(By.id("answer-h[${status.index}]")).isDisplayed());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

}
