package cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        assertThat(cache.add(base)).isTrue();
        Base secondBase = new Base(1,0);
        assertThat(cache.add(secondBase)).isFalse();
    }

    @Test
    public void whenAddAndDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        assertThat(cache.add(base)).isTrue();
        cache.delete(base);
        Base secondBase = new Base(1,0);
        assertThat(cache.add(secondBase)).isTrue();
    }

    @Test
    public void whenDeleteDifferentVersion() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Base secondBase = new Base(1,1);
        cache.delete(secondBase);
        assertThat(cache.add(new Base(1, 0))).isFalse();
        assertThat(cache.getBase(1).getVersion()).isEqualTo(0);
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Base secondBase = new Base(1,0);
        secondBase.setName("Second Base");
        assertThat(cache.update(secondBase)).isTrue();
        assertThat(cache.getBase(1).getVersion()).isEqualTo(1);
        assertThat(cache.getBase(1).getName()).isEqualTo("Second Base");
    }

    @Test
    public void whenTryUpdateDifferentVersion() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Base secondBase = new Base(1,1);
        assertThatThrownBy(() -> cache.update(secondBase)).isInstanceOf(OptimisticException.class);
    }

}